package com.ruth.rurucraftsecommerce.user;

import com.ruth.rurucraftsecommerce.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/ruru-crafts")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PreAuthorize("hasAuthority('SCOPE_LIST_USERS')")
    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers(Authentication authentication){

        try{
            List<User> users= userRepository.findAll();
            Response response=new Response(HttpStatus.OK.getReasonPhrase(), "Data retrieved successfully!",users);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            Response response=new Response(HttpStatus.OK.getReasonPhrase(), "Data not retrieved!",null);
            return ResponseEntity.badRequest().body(response);
        }

    }

}
