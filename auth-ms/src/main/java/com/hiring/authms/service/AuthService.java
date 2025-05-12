package com.hiring.authms.service;

import com.hiring.authms.domain.dto.*;
import com.hiring.authms.domain.entity.User;
import com.hiring.authms.domain.mapper.UserMapper;
import com.hiring.authms.exception.LoginException;
import com.hiring.authms.exception.RegisterException;
import com.hiring.authms.repository.UserDetailsRepository;
import com.hiring.authms.service.producer.OtpProducerService;
import com.hiring.authms.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AuthService {
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsRepository userDetailsRepository;
    private final UserMapper userMapper;
    private final OtpProducerService otpProducerService;
    private final PasswordEncoder passwordEncoder;

    public AuthService(final JwtUtil jwtUtil,
                       final AuthenticationManager authenticationManager,
                       final UserDetailsRepository userDetailsRepository,
                       final UserMapper userMapper,
                       final OtpProducerService otpProducerService,
                       final PasswordEncoder passwordEncoder) {
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.userDetailsRepository = userDetailsRepository;
        this.userMapper = userMapper;
        this.otpProducerService = otpProducerService;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponse login(AuthRequest authRequest) {
        try{
            User user = authenticate(authRequest.email(), authRequest.password());
            String otp = generateOtp();
            otpCache.put(authRequest.email(), otp);

            otpProducerService.sendMessageToNotificationMS(new OtpMessage(
                    "hiringAuthOtpTopicExchange",
                    "auth.otp",
                    authRequest.email(),
                    otp
            ));
            return new AuthResponse("OTP sent to " + authRequest.email());
        } catch (Exception e) {
            throw new LoginException(e.getMessage());
        }
    }

    public RegisterResponse register(RegisterRequest registerRequest){
        try{
            userDetailsRepository.save(userMapper.toUser(registerRequest));

            User user = authenticate(registerRequest.email(), registerRequest.password());

            String otp = generateOtp();
            otpCache.put(registerRequest.email(), otp);

            otpProducerService.sendMessageToNotificationMS(new OtpMessage(
                    "hiringAuthOtpTopicExchange",
                    "auth.otp",
                    registerRequest.email(),
                    otp
            ));
            return new RegisterResponse("OTP sent to " + registerRequest.email());
        } catch (Exception e) {
            throw new RegisterException(e.getMessage());
        }
    }

    public VerifyOtpResponse verifyOtp(VerifyOtpRequest verifyOtpRequest){
        String email = verifyOtpRequest.email();
        String otp = verifyOtpRequest.otp();

        if (otpCache.getOrDefault(email, null) != null
        && otpCache.get(email).equals(otp)) {
            User user = userDetailsRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            otpCache.remove(email);
            return new VerifyOtpResponse(true, jwtUtil.generateToken(user));
        }
        return new VerifyOtpResponse(false, null);
    }

    public String forgetPassword(ForgotPasswordRequest forgotPasswordRequest){
        String email = forgotPasswordRequest.email();

        User user = userDetailsRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String otp = generateOtp();
        otpCache.put(forgotPasswordRequest.email(), otp);
        otpProducerService.sendMessageToNotificationMS(new OtpMessage(
                "hiringAuthOtpTopicExchange",
                "auth.otp",
                email,
                otp
        ));

        return "OTP sent to " + email;
    }

    public boolean resetPassword(ResetPasswordRequest resetPasswordRequest){
        String email = resetPasswordRequest.email();
        String otp = resetPasswordRequest.otp();
        String newPassword = passwordEncoder.encode(resetPasswordRequest.newPassword());

        if (otpCache.getOrDefault(email, null) != null
        && otpCache.get(email).equals(otp)) {
            User user = userDetailsRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            user.setPassword(newPassword);
            userDetailsRepository.save(user);
            otpCache.remove(email);
            return true;
        }
        return false;
    }


    private User authenticate(String username, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        return userDetailsRepository.findByEmail(username)
                .orElseThrow(() -> new LoginException("User not found"));

    }



    private final Map<String, String> otpCache = new ConcurrentHashMap<>();
    private String generateOtp() {
        StringBuilder otp = new StringBuilder();
        Random random = new SecureRandom();
        for (int i = 0; i < 6; i++) {
            otp.append(random.nextInt(10));
        }
        return otp.toString();
    }

}
