package com.example.csws.config;

import com.example.csws.config.jwt.JwtProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

   @Bean
   public CorsFilter corsFilter() {
      UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
      CorsConfiguration config = new CorsConfiguration();
      config.setAllowCredentials(true);       // 내 서버가 응답할 때 json을 JS에서 처리할 수 있게 설정
      config.addAllowedOriginPattern("http://localhost:3000");           // 모든 ip에 응답을 허용
      config.addAllowedHeader("*");           // 모든 header에 응답 허용
      config.addAllowedMethod("*");           // 모든 post,get,put,delete,patch 요청허용
      config.addExposedHeader("Authorization");
      config.addExposedHeader("Set-Cookie");
      source.registerCorsConfiguration("/**",config);
      return new CorsFilter(source);
   }

}