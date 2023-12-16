package com.ruth.rurucraftsecommerce.user;

import com.ruth.rurucraftsecommerce.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

@Tag(name = "2. Users ",description = "Viewing and updating of users")

@RestController
@RequestMapping("/ruru-crafts")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @Operation(summary = "This endpoint is used for listing all the users, you have to be authorized to use this endpoint")
    @PreAuthorize("hasAuthority('SCOPE_LIST_USERS')")
    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers(Authentication authentication){

        try{
            List<UserDTO.ViewUserDTO> users= userService.getAllUsers();
            Response response=new Response(HttpStatus.OK.value(), "Data retrieved successfully!",users);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            Response response=new Response(HttpStatus.OK.value(), "Data not retrieved!",null);
            return ResponseEntity.badRequest().body(response);
        }

    }

    @Operation(summary = "This endpoint is used for retrieving a user, you have to be authorized to use this endpoint")
    @PreAuthorize("hasAuthority('SCOPE_LIST_USERS')")
    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") Integer id){

        try{
            UserDTO.ViewUserDTO user = userService.getUserById(id);
            if(user==null){
                throw new UsernameNotFoundException("User not found with ID: " + id);

            }
            Response response=new Response(HttpStatus.OK.value(), "Data retrieved successfully!",user);
            return ResponseEntity.ok(response);
        }catch (AccessDeniedException e) {
            // Catch AccessDeniedException if not authorized
            Response response = new Response(HttpStatus.FORBIDDEN.value(), "Access denied!", null);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }catch (Exception e){
            Response response=new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Data not retrieved!",null);
            return ResponseEntity.badRequest().body(response);
        }

    }
    @Operation(summary = "This endpoint is used for enabling or disabling a user, you have to be authorized to use this endpoint")
    @PreAuthorize("hasAuthority('SCOPE_LIST_USERS')")
    @PutMapping("/user/enable/{id}")
    public ResponseEntity<?> enableUser(@PathVariable("id") Integer id, @RequestBody UserDTO.UserEnableAccountDTO enableAccount){

        try {
            UserDTO.ViewUserDTO user=userService.enableOrDisableUser(id, enableAccount);
            Response response=new Response(HttpStatus.OK.value(), User.class.getSimpleName()+" updated successfully!",user);
            return ResponseEntity.ok(response);
        } catch (NoSuchElementException e) {
            // Handle the case where the user with the given ID is not found
            Response response=new Response(HttpStatus.NOT_FOUND.value(), User.class.getSimpleName()+" not found with ID:"+id,null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            // Handle other exceptions
            Response response=new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal server error",null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }


    }

    @Operation(summary = "This endpoint is used for locking or unlocking  an account, you have to be authorized to use this endpoint")
    @PreAuthorize("hasAuthority('SCOPE_LIST_USERS')")
    @PutMapping("/user/unlock/{id}")
    public ResponseEntity<?> enableUser(@PathVariable("id") Integer id, @RequestBody UserDTO.UserUnlockAccountDTO unlockAccountDTO){

        try {
            UserDTO.ViewUserDTO user=userService.unlockOrLockAccount(id, unlockAccountDTO);
            Response response=new Response(HttpStatus.OK.value(), User.class.getSimpleName()+" updated successfully!",user);
            return ResponseEntity.ok(response);
        } catch (NoSuchElementException e) {
            // Handle the case where the user with the given ID is not found
            Response response=new Response(HttpStatus.NOT_FOUND.value(), User.class.getSimpleName()+" not found with ID:"+id,null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            // Handle other exceptions
            Response response=new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal server error",null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }


    }

    @Operation(summary = "This endpoint is used for updating user details, you have to be authorized to use this endpoint")
    @PreAuthorize("hasAuthority('SCOPE_LIST_USERS')")
    @PutMapping("/user/update/{id}")
    public ResponseEntity<?> enableUser(@PathVariable("id") Integer id, @RequestBody UserDTO.UserDetailsUpdateDTO updateDTO){

        try {
            UserDTO.ViewUserDTO user=userService.updateUserDetails(id, updateDTO);
            Response response=new Response(HttpStatus.OK.value(), User.class.getSimpleName()+" updated successfully!",user);
            return ResponseEntity.ok(response);
        } catch (NoSuchElementException e) {
            // Handle the case where the user with the given ID is not found
            Response response=new Response(HttpStatus.NOT_FOUND.value(), User.class.getSimpleName()+" not found with ID:"+id,null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            // Handle other exceptions
            Response response=new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal server error",null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }


    }



}
