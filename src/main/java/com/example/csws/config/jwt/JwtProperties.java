package com.example.csws.config.jwt;

public interface JwtProperties {
	String SECRET = "123154123123"; // private key
	int EXPIRATION_TIME = 864000000; // 10일 (1/1000초)
	String TOKEN_PREFIX = "Bearer ";
	String HEADER_STRING = "Authorization";
}
