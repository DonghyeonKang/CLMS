package com.example.csws.config.jwt;

public interface JwtProperties {
	String SECRET = "1sd2f3g1h5f4dg123ga1dfa2d3fg12v6d8f8gs9na3fwr3"; // private key
	int REFRESH_EXPIRATION_TIME = 864000000;  // 10일 (1/1000초)
	int ACCESS_EXPIRATION_TIME = 300000; // 5분
	String TOKEN_PREFIX = "Bearer ";
	String ACCESS_HEADER_PREFIX = "Authorization";
	String REFRESH_HEADER_PREFIX = "";
	String ACCESS_TOKEN = "ngofadsgnid";
	String REFRESH_TOKEN = "refresh";
}
