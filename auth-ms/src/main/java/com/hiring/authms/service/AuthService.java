package com.hiring.authms.service;

import com.hiring.authms.domain.dto.AuthRequest;
import com.hiring.authms.domain.dto.AuthResponse;
import com.hiring.authms.domain.dto.RegisterRequest;
import com.hiring.authms.domain.dto.RegisterResponse;
import com.hiring.authms.domain.mapper.UserMapper;
import com.hiring.authms.repository.UserDetailsRepository;
import com.hiring.authms.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsRepository userDetailsRepository;
    private final UserMapper userMapper;

    public AuthService(final JwtUtil jwtUtil,
                       final AuthenticationManager authenticationManager,
                       final UserDetailsRepository userDetailsRepository,
                       final UserMapper userMapper) {
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.userDetailsRepository = userDetailsRepository;
        this.userMapper = userMapper;
    }

    public AuthResponse authenticate(AuthRequest authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.username(),
                        authRequest.password())
        );
        return new AuthResponse(jwtUtil.generateToken(authRequest.username()));
    }

    public RegisterResponse register(RegisterRequest registerRequest){
        userDetailsRepository.save(userMapper.toUser(registerRequest));

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(registerRequest.username(),
                        registerRequest.password())
        );
        return new RegisterResponse(true, jwtUtil.generateToken(registerRequest.username()));
    }
}
