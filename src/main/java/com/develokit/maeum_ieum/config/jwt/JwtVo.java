package com.develokit.maeum_ieum.config.jwt;

import org.springframework.beans.factory.annotation.Value;

public class JwtVo {
    @Value("${jwt.secret}")
    public static String SECRET;

    @Value("${jwt.expiration.time}")
    public static int EXPIRATION_TIME;

    @Value("${jwt.prefix}")
    public static String TOKEN_PREFIX;

    @Value("${jwt.header}")
    public static  String HEADER;
}
