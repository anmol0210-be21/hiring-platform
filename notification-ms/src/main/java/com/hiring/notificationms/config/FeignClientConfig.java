package com.hiring.notificationms.config;

import com.hiring.notificationms.security.JwtContextHolder;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            String token = JwtContextHolder.getToken();
            if (token != null) {
                requestTemplate.header("Authorization", "Bearer " + token);
            }
        };
    }
}
