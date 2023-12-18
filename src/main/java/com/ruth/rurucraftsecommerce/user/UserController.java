package com.ruth.rurucraftsecommerce.user;

import com.ruth.rurucraftsecommerce.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

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
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers(Authentication authentication){
        System.out.println(authentication.getAuthorities().toString());

        try{
            List<UserDTO.ViewUserDTO> users= userService.getAllUsers();
            Response response=new Response(HttpStatus.OK.value(), "Data retrieved successfully!",users);
            return ResponseEntity.ok(response);
        }catch (AccessDeniedException e){
            Response response=new Response(HttpStatus.UNAUTHORIZED.value(), "Data not retrieved!",null);
            return ResponseEntity.badRequest().body(response);
        }catch (Exception e){
            Response response=new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Data not retrieved!",null);
            return ResponseEntity.badRequest().body(response);
        }

    }

    @Operation(summary = "This endpoint is used for retrieving a user, you have to be authorized to use this endpoint")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')  or  @userServiceImpl.isResourceOwner(authentication,#id)")
    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") Integer id){


        try{
            UserDTO.ViewUserDTO user = userService.getUserById(id);

            Response response=new Response(HttpStatus.OK.value(), "Data retrieved successfully!",user);
            return ResponseEntity.ok(response);
        }catch (NoSuchElementException e){
            Response response=new Response(HttpStatus.BAD_REQUEST.value(), e.getMessage(),null);
            return ResponseEntity.badRequest().body(response);
        }catch (AccessDeniedException e) {
            // Catch AccessDeniedException if not authorized
            Response response = new Response(HttpStatus.FORBIDDEN.value(), e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }catch (Exception e){
            Response response=new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Data not retrieved!",null);
            return ResponseEntity.badRequest().body(response);
        }

    }

    @Operation(summary = "This endpoint is used for updating user details, you have to be authorized to use this endpoint")
    @PreAuthorize("hasAuthority('SCOPE_CUSTOMER')&& @userServiceImpl.isResourceOwner(authentication,#id)")
    @PutMapping("/user/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") Integer id, @RequestBody UserDTO.UserDetailsUpdateDTO updateDTO,Authentication authentication){

        try {
            UserDTO.ViewUserDTO userRetrieved = userService.getUserById(id);

            UserDTO.ViewUserDTO user=userService.updateUserDetails(userRetrieved.getId(), updateDTO);
            Response response=new Response(HttpStatus.OK.value(), User.class.getSimpleName()+" updated successfully!",user);
            return ResponseEntity.ok(response);
        } catch (NoSuchElementException e) {
            // Handle the case where the user with the given ID is not found
            Response response=new Response(HttpStatus.NOT_FOUND.value(), User.class.getSimpleName()+" not found with ID:"+id,null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (AccessDeniedException e) {
            // Catch AccessDeniedException if not authorized
            Response response = new Response(HttpStatus.UNAUTHORIZED.value(), e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }catch (Exception e) {
            // Handle other exceptions
            Response response=new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal server error",null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }


    }

    @Operation(summary = "This endpoint is used for deleting a user, you have to be authorized to use this endpoint")
    @PreAuthorize("@userServiceImpl.isResourceOwner(authentication,#id)")
    @DeleteMapping("/user/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Integer id,Authentication authentication){

        try {
            UserDTO.ViewUserDTO userRetrieved = userService.getUserById(id);
            boolean deleted=userService.deleteUser(userRetrieved.getId());
            System.out.println(deleted);
            Response response=new Response(HttpStatus.OK.value(), User.class.getSimpleName()+" deleted successfully!",null);
            return ResponseEntity.ok(response);
        } catch (NoSuchElementException e) {
            // Handle the case where the user with the given ID is not found
            Response response=new Response(HttpStatus.NOT_FOUND.value(), User.class.getSimpleName()+" not found with ID:"+id,null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (AccessDeniedException e) {
            // Catch AccessDeniedException if not authorized
            Response response = new Response(HttpStatus.FORBIDDEN.value(), e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }catch (Exception e) {
            // Handle other exceptions
            Response response=new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal server error",null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }


    }



}
