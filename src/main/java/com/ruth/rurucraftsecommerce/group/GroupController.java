package com.ruth.rurucraftsecommerce.group;

import com.ruth.rurucraftsecommerce.permissions.Permission;
import com.ruth.rurucraftsecommerce.permissions.PermissionServiceImpl;
import com.ruth.rurucraftsecommerce.response.Response;
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
import java.util.NoSuchElementException;

@Tag(name = "4. Groups ",description = "Interact with groups in a system. You can create new ones, modify existing ones, list, or remove ones that are no longer necessary.")

@RestController
@RequestMapping("/ruru-crafts")
public class GroupController {
    @Autowired
    private GroupServiceImpl groupService;

    @Autowired
    private PermissionServiceImpl permissionService;

    @Operation(summary = "This endpoint creates a groups and requires user to be authenticated")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PostMapping("/group/create")
    public ResponseEntity<?> createGroup(@RequestBody Group group){

        try{
            Group createdGroup= groupService.createGroup(group);
            Response response=new Response(HttpStatus.OK.value(), "Data created successfully!",createdGroup);
            return ResponseEntity.ok(response);
        }catch (AccessDeniedException e){
            Response response=new Response(HttpStatus.UNAUTHORIZED.value(), "Data not retrieved!",null);
            return ResponseEntity.badRequest().body(response);
        }catch (Exception e){
            Response response=new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Data not retrieved!",null);
            return ResponseEntity.badRequest().body(response);
        }

    }

    @Operation(summary = "This endpoint retrieves all groups and requires user to be authenticated")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping("/groups")
    public ResponseEntity<?> getAllGroups(Authentication authentication){

        try{
            List<Group> groups= groupService.getAllGroups();
            Response response=new Response(HttpStatus.OK.value(), "Data retrieved successfully!",groups);
            return ResponseEntity.ok(response);
        }catch (AccessDeniedException e){
            Response response=new Response(HttpStatus.UNAUTHORIZED.value(), "Data not retrieved!",null);
            return ResponseEntity.badRequest().body(response);
        }catch (Exception e){
            Response response=new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Data not retrieved!",null);
            return ResponseEntity.badRequest().body(response);
        }

    }
    @Operation(summary = "This endpoint retrieves  a group by the id and requires user to be authenticated")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping("/group/{id}")
    public ResponseEntity<?> getGroupsById(@PathVariable("id") Integer id){

        try{
            Group group= groupService.getGroupById(id);
            Response response=new Response(HttpStatus.OK.value(), "Data retrieved successfully!",group);
            return ResponseEntity.ok(response);
        }catch (AccessDeniedException e){
            Response response=new Response(HttpStatus.UNAUTHORIZED.value(), "Data not retrieved!",null);
            return ResponseEntity.badRequest().body(response);
        }catch (Exception e){
            Response response=new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Data not retrieved!",null);
            return ResponseEntity.badRequest().body(response);
        }

    }

    @Operation(summary = "This endpoint updates a group and requires user to be authenticated")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PutMapping("/group/update/{id}")
    public ResponseEntity<?> updateGroup(@PathVariable("id") Integer id,@RequestBody Group group){

        try{
            Group retrievedGroup= groupService.getGroupById(id);
            Group createdGroup= groupService.updateGroup(id,group);
            Response response=new Response(HttpStatus.OK.value(), "Data updated successfully!",createdGroup);
            return ResponseEntity.ok(response);
        }catch (AccessDeniedException e){
            Response response=new Response(HttpStatus.UNAUTHORIZED.value(), "Data not retrieved!",null);
            return ResponseEntity.badRequest().body(response);
        }catch (Exception e){
            Response response=new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Data not retrieved!",null);
            return ResponseEntity.badRequest().body(response);
        }

    }

    @Operation(summary = "This endpoint delete a group and requires user to be authenticated")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @DeleteMapping("/group/delete/{id}")
    public ResponseEntity<?> deleteGroup(@PathVariable("id") Integer id){

        try{
            Group retrievedGroup= groupService.getGroupById(id);
            boolean createdGroup= groupService.deleteGroup(id);
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

    @Operation(summary = "This endpoint creates a relationship between a group and permission and requires user to be authenticated")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PostMapping("/group/{groupId}/create/permission")
    public ResponseEntity<?> createGroupPermission(@PathVariable("groupId") Integer groupId, @RequestBody List<Integer> permissionIds) {
        Group retrievedGroup= groupService.getGroupById(groupId);
        try {
            boolean created=groupService.createGroupWithPermissions(groupId,permissionIds);
            Response response=new Response(HttpStatus.OK.value(), "Data created successfully!",null);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Response response=new Response(HttpStatus.BAD_REQUEST.value(), "Combination already exists!",null);
            return ResponseEntity.badRequest().body(response);
        }catch (AccessDeniedException e){
            Response response=new Response(HttpStatus.UNAUTHORIZED.value(), "Data not retrieved!",null);
            return ResponseEntity.badRequest().body(response);
        }catch (Exception e){
            Response response=new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Data not retrieved!",null);
            return ResponseEntity.badRequest().body(response);
        }


    }

    @Operation(summary = "This endpoint deletes a relationship between a group and permission and requires user to be authenticated")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @DeleteMapping("/group/{groupId}/delete/permission/{permissionId}")
    public ResponseEntity<?> deleteGroupPermission(@PathVariable("groupId") Integer groupId,@PathVariable("permissionId") Integer permissionId ) {
        Group retrievedGroup= groupService.getGroupById(groupId);
        Permission retrievedPermission= permissionService.getPermissionById(permissionId);
        try {
            boolean deleted=groupService.deletGroupPermissions(groupId,permissionId);
            Response response=new Response(HttpStatus.OK.value(), "Data deleted successfully!",null);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Response response=new Response(HttpStatus.BAD_REQUEST.value(), "Combination already exists!",null);
            return ResponseEntity.badRequest().body(response);
        }catch (NoSuchElementException e) {
            Response response=new Response(HttpStatus.BAD_REQUEST.value(), e.getMessage(),null);
            return ResponseEntity.badRequest().body(response);
        } catch (AccessDeniedException e){
            Response response=new Response(HttpStatus.UNAUTHORIZED.value(), "Data not retrieved!",null);
            return ResponseEntity.badRequest().body(response);
        }catch (Exception e){
            Response response=new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Data not retrieved!",null);
            return ResponseEntity.badRequest().body(response);
        }


    }
    @Operation(summary = "This endpoint retrieves a relationship between a group and permission and requires user to be authenticated")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping("/group/{groupId}/permissions")
    public ResponseEntity<?> getPermissionsByGroupId(@PathVariable Integer groupId) {
        Group retrievedGroup= groupService.getGroupById(groupId);
        try {
            List<Permission> permissions = groupService.getPermissionsByGroupId(groupId);
            Response response=new Response(HttpStatus.OK.value(), "Data retrieved successfully!",permissions);
            return ResponseEntity.ok(response);
        }catch (NoSuchElementException e) {
            Response response=new Response(HttpStatus.BAD_REQUEST.value(), e.getMessage(),null);
            return ResponseEntity.badRequest().body(response);
        } catch (AccessDeniedException e){
            Response response=new Response(HttpStatus.UNAUTHORIZED.value(), "Data not retrieved!",null);
            return ResponseEntity.badRequest().body(response);
        }catch (Exception e){
            Response response=new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Data not retrieved!",null);
            return ResponseEntity.badRequest().body(response);
        }
    }



}


