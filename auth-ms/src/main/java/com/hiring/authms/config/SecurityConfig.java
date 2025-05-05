package com.hiring.authms.config;

import com.hiring.authms.domain.entity.User;
import com.hiring.authms.repository.UserDetailsRepository;
import com.hiring.authms.service.CustomUserDetailsService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final CustomUserDetailsService customUserDetailsService;
    private final UserDetailsRepository userDetailsRepository;

    public SecurityConfig(final CustomUserDetailsService customUserDetailsService,
                          final UserDetailsRepository userDetailsRepository) {
        this.customUserDetailsService = customUserDetailsService;
        this.userDetailsRepository = userDetailsRepository;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
        http.
            httpBasic(Customizer.withDefaults())
            .authorizeHttpRequests(authReq -> authReq
                    .requestMatchers("/auth/**").permitAll()
            .anyRequest().authenticated());

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(PasswordEncoder passwordEncoder) throws Exception {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(authProvider);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



    // to create default admin
    @Bean
    public CommandLineRunner createAdmin(PasswordEncoder passwordEncoder) {
        return args -> {
            if (userDetailsRepository.findByUsername("admin").isEmpty()) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin"));
                admin.setRole("ADMIN");
                userDetailsRepository.save(admin);
            }
        };
    }
}
