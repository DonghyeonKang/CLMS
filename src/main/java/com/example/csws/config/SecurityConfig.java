package com.example.csws.config;


import com.example.csws.config.jwt.JwtAuthenticationFilter;
import com.example.csws.config.jwt.JwtAuthorizationFilter;
import com.example.csws.config.jwt.JwtService;
import com.example.csws.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity // 시큐리티 활성화 -> 기본 스프링 필터체인에 등록
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	private final UserRepository userRepository;
	private final JwtService jwtService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.cors().configurationSource(corsConfigurationSource())
				.and()
				.csrf().disable()
 				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
				.formLogin().disable()
				.httpBasic().disable()
				// 필터 추가
				.addFilter(new JwtAuthenticationFilter(authenticationManager(), jwtService))	// 모든 인증 요청시에 실행 /login
				.addFilter(new JwtAuthorizationFilter(authenticationManager(), userRepository, jwtService))	// request 가 controller 에 접근하기 전에 해당 필터를 수행함
				// 권한 관리
				.authorizeRequests() // security 처리에 HttpServletRequest 를 이용한다.
				.requestMatchers(CorsUtils::isPreFlightRequest).permitAll()	// cors preflight 요청은 인증 처리를 하지 않음
				.antMatchers("/", "/**") // 경로 지정
					.permitAll() // 모든 사용자 접근 허용
				.antMatchers("/user/university")
					.permitAll()
				.antMatchers("/instances")// instance 관리하는 작업은 모두 user, manager, admin 권한이 있어야한다.
					.access("hasRole('ROLE_USER', 'ROLE_MANAGER', 'ROLE_ADMIN')") // user, manager, admin 이 접근 가능
				.anyRequest() // 다른 모든 요청
					.permitAll();
	}

	//CORS 설정
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();

		config.setAllowCredentials(true);
		config.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://csws.kro.kr"));
		config.setAllowedMethods(Arrays.asList("HEAD","POST","GET","DELETE","PUT"));
		config.setAllowedHeaders(Arrays.asList("*"));

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
	}
}






