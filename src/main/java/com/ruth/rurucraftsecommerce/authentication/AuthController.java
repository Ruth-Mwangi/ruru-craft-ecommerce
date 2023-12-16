package com.ruth.rurucraftsecommerce.authentication;

import com.ruth.rurucraftsecommerce.response.Response;
import com.ruth.rurucraftsecommerce.user.User;
import com.ruth.rurucraftsecommerce.user.UserRepository;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "1. Authentication",description = "Creation and authentication of users")
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

    @Operation(summary = "This endpoint is used for login and authentication.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation",
                content = @Content(mediaType = "application/json",
                        examples = @ExampleObject(name = "Log in example",
                                value = "{\n" +
                                        "    \"message\": \"User logged in successfully\",\n" +
                                        "    \"token\": \"abcde.123455\"\n" +
                                        "}")))
    })
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

            Response response = new Response(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
            return ResponseEntity.badRequest().body(response);
        }


    }
    @Operation(summary = "This endpoint is used for user registration.")
    @PostMapping("/register")
    public ResponseEntity<?> login(@RequestBody User registerUser) throws IllegalAccessException {

        try{

            registerUser.setPassword(passwordEncoder.encode(registerUser.getPassword()));

            User createdUser=userRepository.save(registerUser);
            createdUser.setPassword(null);
            Response response = new Response(HttpStatus.OK.value(),"User registered successfully, proceed to log in.",createdUser);


            return ResponseEntity.ok(response);

        }catch (Exception e) {
            Response response = new Response(HttpStatus.BAD_REQUEST.value(), e.getCause().getMessage(), null);
            return ResponseEntity.badRequest().body(response);
        }

    }
}
