package com.example.csws.config.jwt;

import com.example.csws.common.exception.ErrorCode;
import com.example.csws.config.LoginRequestDto;
import com.example.csws.config.auth.PrincipalDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.json.simple.JSONObject;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	
	// Authentication 객체 만들어서 리턴 => 의존 : AuthenticationManager
	// 인증 요청시에 실행되는 함수 => /login
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		System.out.println("JwtAuthenticationFilter : 진입");

		// 1. username, password 가 정상이다.
		// request에 있는 username과 password를 파싱해서 자바 Object로 받기
		ObjectMapper om = new ObjectMapper();
		LoginRequestDto loginRequestDto = null;
		try {
			loginRequestDto = om.readValue(request.getInputStream(), LoginRequestDto.class);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}


		// 2. authenticationToken 생성한다.
		// 유저네임패스워드 토큰 생성
		UsernamePasswordAuthenticationToken authenticationToken =
				new UsernamePasswordAuthenticationToken(
						loginRequestDto.getUsername(), 
						loginRequestDto.getPassword());
		
		System.out.println("JwtAuthenticationFilter : 토큰생성완료");
		System.out.println(authenticationToken);
		// authenticate() 함수가 호출 되면 인증 프로바이더가 유저 디테일 서비스의
		// loadUserByUsername(토큰의 첫번째 파라메터) 를 호출하고
		// UserDetails를 리턴받아서 토큰의 두번째 파라메터(credential)과
		// UserDetails(DB값)의 getPassword()함수로 비교해서 동일하면
		// Authentication 객체를 만들어서 필터체인으로 리턴해준다.
		
		// Tip: 인증 프로바이더의 디폴트 서비스는 UserDetailsService 타입
		// Tip: 인증 프로바이더의 디폴트 암호화 방식은 BCryptPasswordEncoder
		// 결론은 인증 프로바이더에게 알려줄 필요가 없음.
		System.out.println("Authentication 생성");
		Authentication authentication = authenticationManager.authenticate(authenticationToken);
		// 생성된 Authentication 출력
		PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
		System.out.println("Authentication : "+ principalDetails.getUser().getId());
		return authentication;
	}

	// 검증 결과가 성공이라면
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
											Authentication authResult) throws IOException, ServletException {
		System.out.println("successfulAuthentication 진입");
		PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();

		// jwt access token 생성
		String accessToken = jwtService.createAccessToken(Long.valueOf(principalDetails.getUser().getId()), principalDetails.getUser().getUsername());

		// jwt refresh token 생성
		String refreshToken = jwtService.createRefreshToken();

		// refresh token db 에 저장
		jwtService.setRefreshToken(refreshToken, principalDetails.getUser());

		// authorization 헤더에 access, 쿠키에 refresh 토큰 전달
		ResponseCookie refreshCookie = ResponseCookie.from(JwtProperties.REFRESH_TOKEN, refreshToken)
				.domain("localhost")
				.maxAge(7 * 24 * 60 * 60) // 7일
				.path("/")	// 다른 엔드포인트로 가도 쿠키를 가져다닐 수 있도록
				.sameSite("Lax")	// 다른 사이트로 쿠키를 보낼 수 있는 지 여부. none 이면 보낼 수 있음 그런데 none 이면 secure 속성도 설정해야함 아니면 경고 메시지와 함께 쿠키를 사용하지 않음. secure 속성은 https 에서만 쿠키를 보내겠다는 속성이다. Strict 옵션은 절대로 보내지 않겠다는 옵션, Lax 옵션은 Strict 에 일부 예외를 두어 적용되는 설정. post delete 요청에서는 쿠키가 전달되지 않는다.
				.httpOnly(true)	// httponly option. 프론트에서 js로 쿠키를 뜯어볼 수 없음
				.build();
		System.out.println("생성된 " + refreshCookie.toString());
		response.setHeader("Authorization", accessToken);
		response.setHeader("Set-Cookie", refreshCookie.toString());

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("success", true);
		jsonObject.put("refreshToken", refreshToken);

		response.getWriter().print(jsonObject);
	}

	// 검증 결과가 실패라면
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
											  AuthenticationException failed) throws IOException, ServletException {
		// 실해 이유를 Response Body 에 담아서 return 한다.
		log.info("인증 실패 : unsuccessfulAuthentication");
		String failReason =
				failed.getMessage().equals(ErrorCode.MEMBER_ERROR_NOT_FOUND_ENG.getMessage())
				? ErrorCode.USER_NOT_FOUND.getMessage()
						: ErrorCode.MEMBER_ERROR_PASSWORD.getMessage();

		setFailReason(response, failReason);
	}

	private void setFailReason(HttpServletResponse response, String message) throws IOException {
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		response.setContentType("application/json;charset=UTF-8");

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("success", false);
		jsonObject.put("code", -1);
		jsonObject.put("message", message);

		response.getWriter().print(jsonObject);
	}
}
