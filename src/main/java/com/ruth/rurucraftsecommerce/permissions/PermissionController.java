package com.ruth.rurucraftsecommerce.permissions;


import com.ruth.rurucraftsecommerce.response.Response;
import com.ruth.rurucraftsecommerce.user.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "3. Permissions ",description = "Interact with permissions in a system. You can create new ones, modify existing ones, list, or remove ones that are no longer necessary.")

@RestController
@RequestMapping("/ruru-crafts")
public class PermissionController {

    @Autowired
    private PermissionServiceImpl permissionService;


    @Operation(summary = "This endpoint creates a permissions and requires user to be authenticated")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PostMapping("/permission/create")
    public ResponseEntity<?> createPermission(@RequestBody Permission permission){

        try{
            Permission createdPermission= permissionService.createPermission(permission);
            Response response=new Response(HttpStatus.OK.value(), "Data created successfully!",createdPermission);
            return ResponseEntity.ok(response);
        }catch (AccessDeniedException e){
            Response response=new Response(HttpStatus.UNAUTHORIZED.value(), "Data not retrieved!",null);
            return ResponseEntity.badRequest().body(response);
        }catch (Exception e){
            Response response=new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Data not retrieved!",null);
            return ResponseEntity.badRequest().body(response);
        }

    }

    @Operation(summary = "This endpoint retrieves all permissions and requires user to be authenticated")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping("/permissions")
    public ResponseEntity<?> getAllPermissions(Authentication authentication){

        try{
            List<Permission> permissions= permissionService.getAllPermissions();
            Response response=new Response(HttpStatus.OK.value(), "Data retrieved successfully!",permissions);
            return ResponseEntity.ok(response);
        }catch (AccessDeniedException e){
            Response response=new Response(HttpStatus.UNAUTHORIZED.value(), "Data not retrieved!",null);
            return ResponseEntity.badRequest().body(response);
        }catch (Exception e){
            Response response=new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Data not retrieved!",null);
            return ResponseEntity.badRequest().body(response);
        }

    }
    @Operation(summary = "This endpoint retrieves  a permission by the id and requires user to be authenticated")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping("/permission/{id}")
    public ResponseEntity<?> getPermissionsById(@PathVariable("id") Integer id){

        try{
            Permission permission= permissionService.getPermissionById(id);
            Response response=new Response(HttpStatus.OK.value(), "Data retrieved successfully!",permission);
            return ResponseEntity.ok(response);
        }catch (AccessDeniedException e){
            Response response=new Response(HttpStatus.UNAUTHORIZED.value(), "Data not retrieved!",null);
            return ResponseEntity.badRequest().body(response);
        }catch (Exception e){
            Response response=new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Data not retrieved!",null);
            return ResponseEntity.badRequest().body(response);
        }

    }

    @Operation(summary = "This endpoint updates a permission and requires user to be authenticated")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PutMapping("/permission/update/{id}")
    public ResponseEntity<?> updatePermission(@PathVariable("id") Integer id,@RequestBody Permission permission){

        try{
            Permission retrievedPermission= permissionService.getPermissionById(id);
            Permission createdPermission= permissionService.updatePermission(id,permission);
            Response response=new Response(HttpStatus.OK.value(), "Data updated successfully!",createdPermission);
            return ResponseEntity.ok(response);
        }catch (AccessDeniedException e){
            Response response=new Response(HttpStatus.UNAUTHORIZED.value(), "Data not retrieved!",null);
            return ResponseEntity.badRequest().body(response);
        }catch (Exception e){
            Response response=new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Data not retrieved!",null);
            return ResponseEntity.badRequest().body(response);
        }

    }

    @Operation(summary = "This endpoint delete a permission and requires user to be authenticated")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @DeleteMapping("/permission/delete/{id}")
    public ResponseEntity<?> deletePermission(@PathVariable("id") Integer id){

        try{
            Permission retrievedPermission= permissionService.getPermissionById(id);
            boolean createdPermission= permissionService.deletePermission(id);
            Response response=new Response(HttpStatus.OK.value(), "Data deleted successfully!",null);
            return ResponseEntity.ok(response);
        }catch (AccessDeniedException e){
            Response response=new Response(HttpStatus.UNAUTHORIZED.value(), "Data not retrieved!",null);
            return ResponseEntity.badRequest().body(response);
        }catch (Exception e){
            Response response=new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Data not retrieved!",null);
            return ResponseEntity.badRequest().body(response);
        }

    }
}
