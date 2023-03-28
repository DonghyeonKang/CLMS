package com.example.csws.config;


import com.example.csws.config.jwt.JwtAuthenticationFilter;
import com.example.csws.config.jwt.JwtAuthorizationFilter;
import com.example.csws.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity // 시큐리티 활성화 -> 기본 스프링 필터체인에 등록
public class SecurityConfig extends WebSecurityConfigurerAdapter{	
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CorsConfig corsConfig;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.addFilter(corsConfig.corsFilter())
				.csrf().disable()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
				.formLogin().disable()
				.httpBasic().disable()
				// 필터 추가
				.addFilter(new JwtAuthenticationFilter(authenticationManager()))
				.addFilter(new JwtAuthorizationFilter(authenticationManager(), userRepository))
				// 권한 관리
				.authorizeRequests()
				.antMatchers("/", "/**").permitAll()
				.antMatchers("/instances")// instance 관리하는 작업은 모두 user, manager, admin 권한이 있어야한다.
				.access("hasRole('ROLE_USER', 'ROLE_MANAGER', 'ROLE_ADMIN')")
				.anyRequest().permitAll();	//다른 요청은 권한 허용
	}
}






