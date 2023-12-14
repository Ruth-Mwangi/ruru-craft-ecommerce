package com.ruth.rurucraftsecommerce.authentication;

import com.ruth.rurucraftsecommerce.response.Response;
import com.ruth.rurucraftsecommerce.user.User;
import com.ruth.rurucraftsecommerce.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

        try{
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
        }catch (Exception e){

            Response response = new Response(HttpStatus.BAD_REQUEST.toString(), e.getMessage(), null);
            return ResponseEntity.badRequest().body(response);
        }


    }
    @PostMapping("/register")
    public ResponseEntity<?> login(@RequestBody User registerUser) throws IllegalAccessException {

        try{
            User user = new User();
            user.setUsername(registerUser.getUsername());
            user.setLastName(registerUser.getLastName());
            user.setFirstName(registerUser.getFirstName());
            user.setPassword(passwordEncoder.encode(registerUser.getPassword()));

            User createdUser=userRepository.save(user);
            createdUser.setPassword(null);
            Response response = new Response(HttpStatus.OK.toString(),"User registered successfully, proceed to log in.",createdUser);


            return ResponseEntity.ok(response);

        }catch (Exception e) {
            Response response = new Response(HttpStatus.BAD_REQUEST.toString(), e.getCause().getCause().getMessage(), null);
            return ResponseEntity.badRequest().body(response);
        }

    }
}
