package com.example.userservice.service;

import com.example.userservice.dto.request.LoginRequest;
import com.example.userservice.dto.response.LoginResponse;
import com.example.userservice.entity.User;
import com.example.userservice.exception.UserServiceException;
import com.example.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class AuthenticationService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtEncoder jwtEncoder;
    private long expirationInSeconds;

    public AuthenticationService(UserRepository userRepository,
                                 PasswordEncoder passwordEncoder,
                                 JwtEncoder jwtEncoder,
                                 @Value("${security.jwt.token.expiration-seconds}") long expirationInSeconds){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtEncoder = jwtEncoder;
        this.expirationInSeconds = expirationInSeconds;
    }

    public LoginResponse authenticate(LoginRequest request){

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UserServiceException(HttpStatus.NOT_FOUND, "User with username : " + request.getUsername() + " not found"));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new UserServiceException(HttpStatus.UNAUTHORIZED, "Invalid Password");
        }

        Instant issuedAt = Instant.now();

        JwtClaimsSet.Builder claims = JwtClaimsSet.builder()
                .issuer("user-service")
                .issuedAt(issuedAt)
                .expiresAt(issuedAt.plusSeconds(expirationInSeconds))
                .subject(user.getUsername())
                .claim("role", user.getRole().getRole());

        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();

        String token = jwtEncoder.encode(
                JwtEncoderParameters.from(
                        header, claims.build()
                )).getTokenValue();

        return new LoginResponse(token, "Bearer", user.getUsername(), user.getEmail(), expirationInSeconds);
    }

}
