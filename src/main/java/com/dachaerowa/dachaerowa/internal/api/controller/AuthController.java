package com.dachaerowa.dachaerowa.internal.api.controller;


import com.dachaerowa.dachaerowa.domain.model.Role;
import com.dachaerowa.dachaerowa.domain.model.User;
import com.dachaerowa.dachaerowa.domain.repository.UserRepository;
import com.dachaerowa.dachaerowa.domain.service.impl.CustomUserDetailsService;
import com.dachaerowa.dachaerowa.internal.api.request.LoginRequest;
import com.dachaerowa.dachaerowa.internal.api.request.SignupRequest;
import com.dachaerowa.dachaerowa.internal.api.response.AuthResponse;
import com.dachaerowa.dachaerowa.util.JwtUtil;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
@Validated
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtTokenUtil;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest) {
        if (userRepository.findByUsername(signupRequest.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username is already taken");
        }

        User user = new User();
        user.setUsername(signupRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setEmail(signupRequest.getEmail());
        user.setRole(Role.USER);

        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully");
    }
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );

            final UserDetails userDetails = userDetailsService
                    .loadUserByUsername(loginRequest.getUsername());

            final String jwt = jwtTokenUtil.generateToken(userDetails.getUsername());
            return ResponseEntity.ok(new AuthResponse(jwt));

        } catch (Exception e) {
            logger.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).header("Content-Type", "application/json").body("Invalid credentials");
        }
    }
}

