package com.hiring.notificationms.security;

public class JwtContextHolder {
    private static final ThreadLocal<String> jwtTokenHolder = new ThreadLocal<>();

    public static void setToken(String token) {
        jwtTokenHolder.set(token);
    }

    public static String getToken() {
        return jwtTokenHolder.get();
    }

    public static void clear() {
        jwtTokenHolder.remove();
    }
}
