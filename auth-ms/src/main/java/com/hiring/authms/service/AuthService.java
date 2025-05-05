package com.hiring.authms.service;

import com.hiring.authms.domain.dto.AuthRequest;
import com.hiring.authms.domain.dto.AuthResponse;
import com.hiring.authms.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthService(final JwtUtil jwtUtil,
                       final AuthenticationManager authenticationManager) {
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponse authenticate(AuthRequest authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.username(),
                        authRequest.password())
        );
        return new AuthResponse(jwtUtil.generateToken(authRequest.username()));
    }
}
