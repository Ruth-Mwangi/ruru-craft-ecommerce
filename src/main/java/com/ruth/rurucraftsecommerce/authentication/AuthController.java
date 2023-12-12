package com.ruth.rurucraftsecommerce.authentication;

import com.ruth.rurucraftsecommerce.user.User;
import com.ruth.rurucraftsecommerce.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Validated
public class AuthController {
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtDecoder jwtDecoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthDTO.LoginRequest userLogin)  {

        Authentication authentication =
                authenticationManager
                        .authenticate(new UsernamePasswordAuthenticationToken(
                                userLogin.getUsername(),
                                userLogin.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        AuthUser userDetails = (AuthUser) authentication.getPrincipal();

        String token = authService.generateToken(authentication);

        AuthDTO.Response response = new AuthDTO.Response("User logged in successfully", token);

        return ResponseEntity.ok(response);
    }
    @PostMapping("/register")
    public ResponseEntity<?> login(@RequestBody User registerUser) throws IllegalAccessException {

        User user = new User();
        user.setUsername(registerUser.getUsername());
        user.setLastName(registerUser.getLastName());
        user.setFirstName(registerUser.getFirstName());
        user.setPassword(passwordEncoder.encode(registerUser.getPassword()));


        userRepository.save(user);
        AuthDTO.RegistrationResponse response = new AuthDTO.RegistrationResponse("User registered successfully, proceed to log in.", user.toString());


        return ResponseEntity.ok(response);
    }
}