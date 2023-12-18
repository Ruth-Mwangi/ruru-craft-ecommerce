package com.ruth.rurucraftsecommerce;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruth.rurucraftsecommerce.user.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest
@WebMvcTest(controllers = UserController.class)
@ContextConfiguration(classes = {UserServiceImpl.class, UserRepository.class})
@ComponentScan("com.ruth.rurucraftsecommerce")
public class UserControllerTest {


    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserServiceImpl userService;
    @MockBean
    UserRepository userRepository;


    @Test
    @WithAnonymousUser
    void givenRequestIsAnonymous_whenGetUsersMethod_thenUnauthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.
                get("/ruru-crafts/users"))
                .andExpect(status().isUnauthorized()).andDo(print());
    }

    @Test
    @WithAnonymousUser
    void givenRequestIsAnonymous_whenGetUserByIdMethod_thenUnauthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.
                        get("/ruru-crafts/user/1"))
                .andExpect(status().isUnauthorized()).andDo(print());
    }

    @Test
    @WithAnonymousUser
    void givenRequestIsAnonymous_whenDeleteUserByIdMethod_thenUnauthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.
                        delete("/ruru-crafts/delete/1"))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

    @Test
    @WithAnonymousUser
    void givenRequestIsAnonymous_whenUpdateUserByIdMethod_thenUnauthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.
                        delete("/ruru-crafts/update/1"))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "admin@test.com",authorities = "SCOPE_ADMIN")
    void givenUserIsGrantedWithScopeAdmin_whenGetUsersMethod_thenOk() throws Exception {
        List<User> userList=new ArrayList<>();
        User user1= new User(1,"admin@test.com","jane","doe","+25434343443");
        User user2= new User(2,"customer@test.com","john","doe","+25434343444");
        userList.add(user1);
        userList.add(user2);
        when(userService.getAllUsers()).thenReturn(userList.stream().map(UserMapper::mapToUserDto).toList());

        mockMvc.perform(MockMvcRequestBuilders.get("/ruru-crafts/users")).andExpect(status().isOk())
                .andDo(print());


    }

    @Test
    @WithMockUser(username = "customer@test.com",authorities = "SCOPE_CUSTOMER")
    void givenUserIsNotGrantedWithScopeAdmin_whenGetUsersMethod_thenUnauthorized() throws Exception{
        List<User> userList=new ArrayList<>();
        User user1= new User(1,"admin@test.com","jane","doe","+25434343443");
        User user2= new User(2,"customer@test.com","john","doe","+25434343444");
        userList.add(user1);
        userList.add(user2);
        when(userService.getAllUsers()).thenReturn(userList.stream().map(UserMapper::mapToUserDto).toList());

        mockMvc.perform(MockMvcRequestBuilders.get("/ruru-crafts/users")).andExpect(status().isUnauthorized())
                .andDo(print());

    }

    @Test
    @WithMockUser(username = "admin@test.com",authorities = "SCOPE_ADMIN")
    void givenUserIsGrantedWithScopeAdminAndNotResourceOwner_whenGetUserByIdMethod_thenOk() throws Exception {
        List<User> userList=new ArrayList<>();
        User user1= new User(1,"admin@test.com","jane","doe","+25434343443");
        User user2= new User(2,"customer@test.com","john","doe","+25434343444");
        userList.add(user1);
        userList.add(user2);
        when(userService.getAllUsers()).thenReturn(userList.stream().map(UserMapper::mapToUserDto).toList());

        mockMvc.perform(MockMvcRequestBuilders.get("/ruru-crafts/user/1")).andExpect(status().isOk())
                .andDo(print());


    }

    @Test
    @WithMockUser(username = "customer@test.com",authorities = "SCOPE_CUSTOMER",password = "password")
    void givenUserIsResourceOwnerAndIsNotGrantedWithScopeAdmin_whenGetUserByIdMethod_thenOk() throws Exception {
        // Mock data
        int userId = 2;
        List<User> userList=new ArrayList<>();
        User user1= new User(1,"admin@test.com","jane","doe","+25434343443");
        User user2= new User(2,"customer@test.com","john","doe","+25434343444");
        userList.add(user1);
        userList.add(user2);

        // Mock service response
        when(userService.getUserById(userId)).thenReturn(UserMapper.mapToUserDto(user2));

        // Mock the isResourceOwner method to return true for the authenticated user

        when(userService.isResourceOwner(any(Authentication.class), eq(userId))).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/ruru-crafts/user/{id}", userId))
                .andExpect(status().isOk())
                .andDo(print());


    }

    @Test
    @WithMockUser(username = "customer@test.com",authorities = "SCOPE_CUSTOMER")
    void givenUserIsNotResourceOwnerAndIsNotGrantedWithScopeAdmin_whenGetUserByIdMethod_thenUnauthorized() throws Exception {
        // Mock data
        int userId = 1; // User 1's details are being accessed
        List<User> userList = new ArrayList<>();
        User user1 = new User(1, "admin@test.com", "jane", "doe", "+25434343443");
        User user2 = new User(2, "customer@test.com", "john", "doe", "+25434343444");
        userList.add(user1);
        userList.add(user2);

        // Mock service response
        when(userService.getUserById(userId)).thenReturn(UserMapper.mapToUserDto(user1));

        // Mock the isResourceOwner method in userServiceImpl to return false for the authenticated user
        when(userService.isResourceOwner(any(Authentication.class), eq(userId))).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/ruru-crafts/user/{id}", userId))
                .andExpect(status().isUnauthorized())
                .andDo(print());


    }
    @Test
    @WithMockUser(username = "customer@test.com",authorities = "SCOPE_CUSTOMER")
    void givenUserIsResourceOwner_whenUpdateUserByIdMethod_thenOk() throws Exception {
        // Mock data
        int userId = 2; // User 1's details are being accessed
        List<User> userList = new ArrayList<>();
        User user1 = new User(1, "admin@test.com", "jane", "doe", "+25434343443");
        User user2 = new User(2, "customer@test.com", "john", "doe", "+25434343444");
        userList.add(user1);
        userList.add(user2);

        // Mock service responses
        when(userService.getUserById(userId)).thenReturn(UserMapper.mapToUserDto(user2));
        UserDTO.UserDetailsUpdateDTO updateDTO=new UserDTO.UserDetailsUpdateDTO();
        updateDTO.setId(user2.getId());
        updateDTO.setFirstName(user2.getLastName());
        updateDTO.setLastName(user2.getLastName());
        updateDTO.setEmail(user2.getEmail());
        updateDTO.setPhoneNumber(user2.getPhoneNumber());
        when(userService.updateUserDetails(userId, updateDTO)).thenReturn(UserMapper.mapToUserDto(user2));

        when(userService.isResourceOwner(any(Authentication.class), eq(userId))).thenReturn(true);


        // Perform the request
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/ruru-crafts/user/update/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andDo(print());


    }
    @Test
    @WithMockUser(username = "customer@test.com",authorities = "SCOPE_CUSTOMER")
    void givenUserIsNotResourceOwner_whenUpdateUserByIdMethod_thenUnauthorized() throws Exception {
        // Mock data
        int userId = 1; // User 1's details are being accessed
        List<User> userList = new ArrayList<>();
        User user1 = new User(1, "admin@test.com", "jane", "doe", "+25434343443");
        User user2 = new User(2, "customer@test.com", "john", "doe", "+25434343444");
        userList.add(user1);
        userList.add(user2);

        // Mock service responses
        when(userService.getUserById(userId)).thenReturn(UserMapper.mapToUserDto(user1));
        UserDTO.UserDetailsUpdateDTO updateDTO=new UserDTO.UserDetailsUpdateDTO();
        updateDTO.setId(user1.getId());
        updateDTO.setFirstName(user1.getLastName());
        updateDTO.setLastName(user1.getLastName());
        updateDTO.setEmail(user1.getEmail());
        updateDTO.setPhoneNumber(user1.getPhoneNumber());
        when(userService.updateUserDetails(userId, updateDTO)).thenReturn(UserMapper.mapToUserDto(user1));

        when(userService.isResourceOwner(any(Authentication.class), eq(userId))).thenReturn(false);


        // Perform the request
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/ruru-crafts/user/update/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isUnauthorized())
                .andDo(print());


    }

    @Test
    @WithMockUser(username = "customer@test.com",authorities = "SCOPE_CUSTOMER")
    void givenUserIsResourceOwner_whenDeleteUserMethod_thenOk() throws Exception {
        // Mock data
        int userId = 2; // User 1's details are being accessed
        List<User> userList = new ArrayList<>();
        User user1 = new User(1, "admin@test.com", "jane", "doe", "+25434343443");
        User user2 = new User(2, "customer@test.com", "john", "doe", "+25434343444");
        userList.add(user1);
        userList.add(user2);

        // Mock service responses
        when(userService.getUserById(userId)).thenReturn(UserMapper.mapToUserDto(user2));

        when(userService.deleteUser(userId)).thenReturn(true);

        when(userService.isResourceOwner(any(Authentication.class), eq(userId))).thenReturn(true);


        // Perform the request
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/ruru-crafts/user/delete/{id}", userId))
                .andExpect(status().isOk())
                .andDo(print());


    }
    @Test
    @WithMockUser(username = "customer@test.com",authorities = "SCOPE_CUSTOMER")
    void givenUserIsNotResourceOwner_whenDeleteUserMethod_thenUnauthorized() throws Exception {
        // Mock data
        int userId = 1; // User 1's details are being accessed
        List<User> userList = new ArrayList<>();
        User user1 = new User(1, "admin@test.com", "jane", "doe", "+25434343443");
        User user2 = new User(2, "customer@test.com", "john", "doe", "+25434343444");
        userList.add(user1);
        userList.add(user2);

        // Mock service responses
        when(userService.getUserById(userId)).thenReturn(UserMapper.mapToUserDto(user1));

        when(userService.deleteUser(userId)).thenReturn(false);

        when(userService.isResourceOwner(any(Authentication.class), eq(userId))).thenReturn(false);


        // Perform the request
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/ruru-crafts/user/delete/{id}", userId))
                .andExpect(status().isUnauthorized())
                .andDo(print());


    }


}
