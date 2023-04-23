package com.example.csws.config.jwt;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.csws.common.exception.CustomJwtException;
import com.example.csws.common.exception.JwtErrorCode;
import com.example.csws.config.auth.PrincipalDetails;
import com.example.csws.entity.user.User;
import com.example.csws.repository.user.UserRepository;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

// 인가
public class JwtAuthorizationFilter extends BasicAuthenticationFilter{
	
	private final UserRepository userRepository;
	private final JwtService jwtService;
	
	public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository, JwtService jwtService) {
		super(authenticationManager);
		this.userRepository = userRepository;
		this.jwtService = jwtService;
	}

	// 인증이나 권한이 필요한 주소 요청이 있을 때 해당 필터를 거친다.
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		// header 에 Authorization 이 없다면 필터 반환
		String header = request.getHeader(JwtProperties.ACCESS_HEADER_PREFIX);
		if(header == null || !header.startsWith(JwtProperties.TOKEN_PREFIX)) {
			chain.doFilter(request, response);
			return;
		}

		// 헤더에 access token 과 refresh token 이 모두 있는지 확인
		Map<String, Object> map = jwtService.checkHeaderValid(request);
		String accessToken = map.get("accessToken").toString();
		String refreshToken = map.get("refreshToken").toString();

		// token 검증. ----------------> 로직 개선 필요.
		DecodedJWT decodedJWT = jwtService.checkTokenValid(accessToken);
		String username = null;

		if(decodedJWT != null) {
			// 새 access token 생성
			username = decodedJWT.getClaim("username").asString();
			String id = decodedJWT.getClaim("userid").asString();
			String newAccessToken = jwtService.createAccessToken(Long.parseLong(id), decodedJWT.getClaim("username").asString());

			// response: Authorization 토큰
			response.setHeader("Authorization", newAccessToken);

		} else if (jwtService.checkTokenValid(refreshToken) != null) {
			// 새 refresh token 생성 후 업데이트
			System.out.println(refreshToken);
			User user = jwtService.getUserByRefreshToken(refreshToken);	// user 를 가져옴
			String newRefresh = jwtService.createRefreshToken();	// 새로운 토큰 생성
			jwtService.setRefreshToken(refreshToken, user);	// 새로운 토큰 저장

			// 새 access token 생성
			String newAccessToken = jwtService.createAccessToken(Long.valueOf(user.getId()), user.getUsername());
			username = user.getUsername();

			// response: 쿠키에 refresh 토큰
			ResponseCookie refreshCookie = ResponseCookie.from(JwtProperties.REFRESH_TOKEN, newRefresh)
					.maxAge(7 * 24 * 60 * 60) // 7일
					.path("/")	// 다른 엔드포인트로 가도 쿠키를 가져다닐 수 있도록
					.sameSite("Lax")	// 다른 사이트로 쿠키를 보낼 수 있는 지 여부. none 이면 보낼 수 있음 그런데 none 이면 secure 속성도 설정해야함 아니면 경고 메시지와 함께 쿠키를 사용하지 않음. secure 속성은 https 에서만 쿠키를 보내겠다는 속성이다. Strict 옵션은 절대로 보내지 않겠다는 옵션, Lax 옵션은 Strict 에 일부 예외를 두어 적용되는 설정
					.httpOnly(true)	// httponly option. 프론트에서 js로 쿠키를 뜯어볼 수 없음
					.build();
			response.setHeader("Set-Cookie", refreshCookie.toString());

			// response: Authorization 토큰
			response.setHeader("Authorization", newAccessToken);

		} else {
			System.out.println("refresh 만료됨!");
			throw new CustomJwtException(JwtErrorCode.JWT_REFRESH_NOT_VALID);
		}



		// 토큰 검증 (이게 인증이기 때문에 AuthenticationManager 도 필요 없음). 토큰이 유효하면, 회원을 조회한다.
		// 인증은 토큰 검증시 끝. 인증을 하기 위해서가 아닌 스프링 시큐리티가 수행해주는 권한 처리를 위해
		// 아래와 같이 토큰을 만들어서 Authentication 객체를 강제로 만들고 그걸 세션에 저장!
		// 내가 SecurityContext 에 직접접근해서 세션을 만들때 자동으로 UserDetailsService 에 있는 loadByUsername 이 호출됨.
		if(username != null) {
			User user = userRepository.findByUsername(username)
					.stream()
					.findFirst()
					.orElse(null); // Optional<Object> 를 Object 로 변경

			PrincipalDetails principalDetails = new PrincipalDetails(user);
			Authentication authentication =
					new UsernamePasswordAuthenticationToken(
							principalDetails, //나중에 컨트롤러에서 DI해서 쓸 때 사용하기 편함.
							null, // 패스워드는 모르니까 null 처리, 어차피 지금 인증하는게 아니니까!!
							principalDetails.getAuthorities());
			
			// 강제로 시큐리티의 세션에 접근하여 값 저장
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
	
		chain.doFilter(request, response);	// 필터 반환
	}
}
