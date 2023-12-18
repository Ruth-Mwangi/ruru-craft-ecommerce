package com.ruth.rurucraftsecommerce;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruth.rurucraftsecommerce.permissions.Permission;
import com.ruth.rurucraftsecommerce.permissions.PermissionController;
import com.ruth.rurucraftsecommerce.permissions.PermissionRepository;
import com.ruth.rurucraftsecommerce.permissions.PermissionServiceImpl;
import com.ruth.rurucraftsecommerce.user.UserRepository;
import com.ruth.rurucraftsecommerce.user.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PermissionController.class)
@ContextConfiguration(classes = {PermissionServiceImpl.class, PermissionRepository.class})
@ComponentScan("com.ruth.rurucraftsecommerce")
public class PermissionControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    PermissionServiceImpl permissionService;

    @MockBean
    PermissionRepository permissionRepository;

    @MockBean
    UserServiceImpl userService;
    @MockBean
    UserRepository userRepository;



    @Test
    @WithAnonymousUser
    void givenRequestIsAnonymous_whenGetPermissionsMethod_thenUnauthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.
                        get("/ruru-crafts/permissions"))
                .andExpect(status().isUnauthorized()).andDo(print());
    }

    @Test
    @WithAnonymousUser
    void givenRequestIsAnonymous_whenGetPermissionByIdMethod_thenUnauthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.
                        get("/ruru-crafts/permissions/{id}",1))
                .andExpect(status().isUnauthorized()).andDo(print());
    }

    @Test
    @WithAnonymousUser
    void givenRequestIsAnonymous_whenDeletePermissionMethod_thenUnauthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.
                        delete("/ruru-crafts/permission/delete/{id}",1))
                .andExpect(status().isUnauthorized()).andDo(print());
    }

    @Test
    @WithAnonymousUser
    void givenRequestIsAnonymous_whenUpdatePermissionMethod_thenUnauthorized() throws Exception {
        Permission permission=new Permission();
        permission.setName("Test");
        permission.setId(1);
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.
                        put("/ruru-crafts/permission/update/{id}",1).content(objectMapper.writeValueAsString(permission)))

                .andExpect(status().isUnauthorized()).andDo(print());
    }


    @Test
    @WithMockUser(username = "admin@test.com",authorities = "SCOPE_ADMIN")
    void givenUserIsGrantedWithScopeAdmin_whenGetPermissionsMethod_thenOk() throws Exception {
        List<Permission> permissions=new ArrayList<>();
        Permission permission1= new Permission(1,"LIST_PERMISSIONS");
        Permission permission2= new Permission(2,"CREATE_PERMISSIONS");
        permissions.add(permission1);
        permissions.add(permission2);
        when(permissionService.getAllPermissions()).thenReturn(permissions);

        mockMvc.perform(MockMvcRequestBuilders.get("/ruru-crafts/permissions")).andExpect(status().isOk())
                .andDo(print());


    }

    @Test
    @WithMockUser(username = "customer@test.com",authorities = "SCOPE_CUSTOMER")
    void givenUserIsNotGrantedWithScopeAdmin_whenGetUsersMethod_thenUnauthorized() throws Exception{
        List<Permission> permissions=new ArrayList<>();
        Permission permission1= new Permission(1,"LIST_PERMISSIONS");
        Permission permission2= new Permission(2,"CREATE_PERMISSIONS");
        permissions.add(permission1);
        permissions.add(permission2);
        when(permissionService.getAllPermissions()).thenReturn(permissions);

        mockMvc.perform(MockMvcRequestBuilders.get("/ruru-crafts/permissions")).andExpect(status().isUnauthorized())
                .andDo(print());

    }

    @Test
    @WithMockUser(username = "admin@test.com",authorities = "SCOPE_ADMIN")
    void givenUserIsGrantedWithScopeAdmin_whenGetPermissionByIdMethod_thenOk() throws Exception {

        Integer permissionId=1;
        List<Permission> permissions=new ArrayList<>();
        Permission permission1= new Permission(1,"LIST_PERMISSIONS");
        Permission permission2= new Permission(2,"CREATE_PERMISSIONS");
        permissions.add(permission1);
        permissions.add(permission2);
        when(permissionService.getPermissionById(permissionId)).thenReturn(permission1);

        mockMvc.perform(MockMvcRequestBuilders.get("/ruru-crafts/permission/{id}",permissionId)).andExpect(status().isOk())
                .andDo(print());


    }

    @Test
    @WithMockUser(username = "customer@test.com",authorities = "SCOPE_CUSTOMER",password = "password")
    void givenUserIsNotGrantedWithScopeAdmin_whenGetPermissionByIdMethod_thenUnauthorized() throws Exception {
        // Mock data
        Integer permissionId=1;
        List<Permission> permissions=new ArrayList<>();
        Permission permission1= new Permission(1,"LIST_PERMISSIONS");
        Permission permission2= new Permission(2,"CREATE_PERMISSIONS");
        permissions.add(permission1);
        permissions.add(permission2);
        when(permissionService.getPermissionById(permissionId)).thenReturn(permission1);


        mockMvc.perform(MockMvcRequestBuilders
                        .get("/ruru-crafts/user/{id}", permissionId))
                .andExpect(status().isUnauthorized())
                .andDo(print());


    }

    @Test
    @WithMockUser(username = "admin@test.com",authorities = "SCOPE_ADMIN")
    void givenUserIsGrantedWithScopeAdmin_whenUpdatePermissionByIdMethod_thenOk() throws Exception {
        Integer permissionId=1;
        Permission currentPermission= new Permission(1,"LIST_PERMISSIONS");
        Permission updatedPermission= new Permission(1,"CREATE_PERMISSIONS");

        when(permissionService.getPermissionById(permissionId)).thenReturn(currentPermission);

        when(permissionService.updatePermission(permissionId, updatedPermission)).thenReturn(updatedPermission);


        // Perform the request
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/ruru-crafts/permission/update/{id}", permissionId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedPermission)))
                .andExpect(status().isOk())
                .andDo(print());


    }

    @Test
    @WithMockUser(username = "customer@test.com",authorities = "SCOPE_CUSTOMER")
    void givenUserIsNotGrantedWithScopeAdmin_whenUpdatePermissionByIdMethod_thenUnauthorized() throws Exception {
        Integer permissionId=1;
        Permission currentPermission= new Permission(1,"LIST_PERMISSIONS");
        Permission updatedPermission= new Permission(1,"CREATE_PERMISSIONS");

        when(permissionService.getPermissionById(permissionId)).thenReturn(currentPermission);

        when(permissionService.updatePermission(permissionId, updatedPermission)).thenReturn(updatedPermission);

        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/ruru-crafts/permission/update/{id}", permissionId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedPermission)))
                .andExpect(status().isUnauthorized())
                .andDo(print());


    }

    @Test
    @WithMockUser(username = "admin@test.com",authorities = "SCOPE_ADMIN")
    void givenUserIsGrantedWithScopeAdmin_whenCreatePermissionMethod_thenOk() throws Exception {

        Permission createPermission= new Permission(1,"LIST_PERMISSIONS");


        when(permissionService.createPermission(createPermission)).thenReturn(createPermission);


        // Perform the request
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/ruru-crafts/permission/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createPermission)))
                .andExpect(status().isOk())
                .andDo(print());


    }

    @Test
    @WithMockUser(username = "customer@test.com",authorities = "SCOPE_CUSTOMER")
    void givenUserIsNotGrantedWithScopeAdmin_whenCreatePermissionMethod_thenUnauthorized() throws Exception {
        Permission createPermission= new Permission(1,"LIST_PERMISSIONS");


        when(permissionService.createPermission(createPermission)).thenReturn(createPermission);


        // Perform the request
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/ruru-crafts/permission/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createPermission)))
                .andExpect(status().isUnauthorized())
                .andDo(print());


    }

    @Test
    @WithMockUser(username = "customer@test.com",authorities = "SCOPE_CUSTOMER",password = "password")
    void givenUserIsNotGrantedWithScopeAdmin_whenDeletePermissionByIdMethod_thenUnauthorized() throws Exception {
        Integer permissionId=1;
        LocalDateTime currentDate = LocalDateTime.now();
        Date convertedDate = Date.from(currentDate.atZone(ZoneId.systemDefault()).toInstant());
        List<Permission> permissions=new ArrayList<>();
        Permission currentPermission= new Permission(1,"LIST_PERMISSIONS");
        Permission deletedPermission= new Permission(1,"LIST_PERMISSIONS",convertedDate);

        when(permissionService.getPermissionById(permissionId)).thenReturn(currentPermission);
        when(permissionService.deletePermission(permissionId)).thenReturn(true);


        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/ruru-crafts/permission/delete/{id}", permissionId))
                .andExpect(status().isUnauthorized())
                .andDo(print());


    }

    @Test
    @WithMockUser(username = "admin@test.com",authorities = "SCOPE_ADMIN")
    void givenUserIsGrantedWithScopeAdmin_whenDeletePermissionByIdMethod_thenOk() throws Exception {
        Integer permissionId=1;
        LocalDateTime currentDate = LocalDateTime.now();
        Date convertedDate = Date.from(currentDate.atZone(ZoneId.systemDefault()).toInstant());
        List<Permission> permissions=new ArrayList<>();
        Permission currentPermission= new Permission(1,"LIST_PERMISSIONS");
        Permission deletedPermission= new Permission(1,"LIST_PERMISSIONS",convertedDate);

        when(permissionService.getPermissionById(permissionId)).thenReturn(currentPermission);
        when(permissionService.deletePermission(permissionId)).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/ruru-crafts/permission/delete/{id}", permissionId))
                .andExpect(status().isOk())
                .andDo(print());


    }

}
