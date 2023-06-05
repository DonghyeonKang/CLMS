package com.example.csws.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class CorsConfig implements WebMvcConfigurer {
   @Override
   public void addCorsMappings(CorsRegistry registry) {
      registry.addMapping("/**")
              .allowedOriginPatterns("*") // 모든 출처 허용. 필요에 따라 특정 출처만 지정할 수 있습니다.
              .allowedMethods("GET", "POST", "PUT", "DELETE") // 허용할 HTTP 메소드 지정
              .allowedHeaders("*") // 허용할 요청 헤더 지정
              .allowCredentials(true) // 자격 증명(쿠키, 인증 헤더 등) 허용 여부 설정
              .maxAge(3600); // 사전 요청(preflight)의 유효 기간 설정 (초 단위)
   }
}
