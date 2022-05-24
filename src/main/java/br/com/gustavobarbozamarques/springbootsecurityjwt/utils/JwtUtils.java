package br.com.gustavobarbozamarques.springbootsecurityjwt.utils;

import org.springframework.util.StringUtils;

public class JwtUtils {
    public static String extractJwt(String authorizationHeader) {
        if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }
}
