package com.ruth.rurucraftsecommerce;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruth.rurucraftsecommerce.group.Group;
import com.ruth.rurucraftsecommerce.group.GroupController;
import com.ruth.rurucraftsecommerce.group.GroupRepository;
import com.ruth.rurucraftsecommerce.group.GroupServiceImpl;
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

@WebMvcTest(controllers = GroupController.class)
@ContextConfiguration(classes = {GroupServiceImpl.class, GroupRepository.class})
@ComponentScan("com.ruth.rurucraftsecommerce")
public class GroupControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    GroupServiceImpl groupService;

    @MockBean
    GroupRepository groupRepository;

    @MockBean
    UserServiceImpl userService;
    @MockBean
    UserRepository userRepository;



    @Test
    @WithAnonymousUser
    void givenRequestIsAnonymous_whenGetGroupsMethod_thenUnauthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.
                        get("/ruru-crafts/groups"))
                .andExpect(status().isUnauthorized()).andDo(print());
    }

    @Test
    @WithAnonymousUser
    void givenRequestIsAnonymous_whenGetGroupByIdMethod_thenUnauthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.
                        get("/ruru-crafts/group/{id}",1))
                .andExpect(status().isUnauthorized()).andDo(print());
    }

    @Test
    @WithAnonymousUser
    void givenRequestIsAnonymous_whenDeleteGroupMethod_thenUnauthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.
                        delete("/ruru-crafts/group/delete/{id}",1))
                .andExpect(status().isUnauthorized()).andDo(print());
    }

    @Test
    @WithAnonymousUser
    void givenRequestIsAnonymous_whenUpdateGroupMethod_thenUnauthorized() throws Exception {
        Group group=new Group();
        group.setName("Test");
        group.setId(1);
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.
                        put("/ruru-crafts/group/update/{id}",1).content(objectMapper.writeValueAsString(group)))

                .andExpect(status().isUnauthorized()).andDo(print());
    }


    @Test
    @WithMockUser(username = "admin@test.com",authorities = "SCOPE_ADMIN")
    void givenUserIsGrantedWithScopeAdmin_whenGetGroupsMethod_thenOk() throws Exception {
        List<Group> groups=new ArrayList<>();
        Group group1= new Group(1,"ADMIN");
        Group group2= new Group(2,"CUSTOMER");
        groups.add(group1);
        groups.add(group2);
        when(groupService.getAllGroups()).thenReturn(groups);

        mockMvc.perform(MockMvcRequestBuilders.get("/ruru-crafts/groups")).andExpect(status().isOk())
                .andDo(print());


    }

    @Test
    @WithMockUser(username = "customer@test.com",authorities = "SCOPE_CUSTOMER")
    void givenUserIsNotGrantedWithScopeAdmin_whenGetUsersMethod_thenUnauthorized() throws Exception{
        List<Group> groups=new ArrayList<>();
        Group group1= new Group(1,"ADMIN");
        Group group2= new Group(2,"CUSTOMER");
        groups.add(group1);
        groups.add(group2);
        when(groupService.getAllGroups()).thenReturn(groups);

        mockMvc.perform(MockMvcRequestBuilders.get("/ruru-crafts/groups")).andExpect(status().isUnauthorized())
                .andDo(print());

    }

    @Test
    @WithMockUser(username = "admin@test.com",authorities = "SCOPE_ADMIN")
    void givenUserIsGrantedWithScopeAdmin_whenGetGroupByIdMethod_thenOk() throws Exception {

        Integer groupId=1;
        List<Group> groups=new ArrayList<>();
        Group group1= new Group(1,"ADMIN");
        Group group2= new Group(2,"CUSTOMER");
        groups.add(group1);
        groups.add(group2);
        when(groupService.getGroupById(groupId)).thenReturn(group1);

        mockMvc.perform(MockMvcRequestBuilders.get("/ruru-crafts/group/{id}",groupId)).andExpect(status().isOk())
                .andDo(print());


    }

    @Test
    @WithMockUser(username = "customer@test.com",authorities = "SCOPE_CUSTOMER",password = "password")
    void givenUserIsNotGrantedWithScopeAdmin_whenGetGroupByIdMethod_thenUnauthorized() throws Exception {
        // Mock data
        Integer groupId=1;
        List<Group> groups=new ArrayList<>();
        Group group1= new Group(1,"ADMIN");
        Group group2= new Group(2,"CUSTOMER");
        groups.add(group1);
        groups.add(group2);
        when(groupService.getGroupById(groupId)).thenReturn(group1);


        mockMvc.perform(MockMvcRequestBuilders
                        .get("/ruru-crafts/user/{id}", groupId))
                .andExpect(status().isUnauthorized())
                .andDo(print());


    }

    @Test
    @WithMockUser(username = "admin@test.com",authorities = "SCOPE_ADMIN")
    void givenUserIsGrantedWithScopeAdmin_whenUpdateGroupByIdMethod_thenOk() throws Exception {
        Integer groupId=1;
        Group currentGroup= new Group(1,"ADMIN");
        Group updatedGroup= new Group(1,"CUSTOMER");

        when(groupService.getGroupById(groupId)).thenReturn(currentGroup);

        when(groupService.updateGroup(groupId, updatedGroup)).thenReturn(updatedGroup);


        // Perform the request
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/ruru-crafts/group/update/{id}", groupId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedGroup)))
                .andExpect(status().isOk())
                .andDo(print());


    }

    @Test
    @WithMockUser(username = "customer@test.com",authorities = "SCOPE_CUSTOMER")
    void givenUserIsNotGrantedWithScopeAdmin_whenUpdateGroupByIdMethod_thenUnauthorized() throws Exception {
        Integer groupId=1;
        Group currentGroup= new Group(1,"ADMIN");
        Group updatedGroup= new Group(1,"CUSTOMER");

        when(groupService.getGroupById(groupId)).thenReturn(currentGroup);

        when(groupService.updateGroup(groupId, updatedGroup)).thenReturn(updatedGroup);

        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/ruru-crafts/group/update/{id}", groupId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedGroup)))
                .andExpect(status().isUnauthorized())
                .andDo(print());


    }

    @Test
    @WithMockUser(username = "admin@test.com",authorities = "SCOPE_ADMIN")
    void givenUserIsGrantedWithScopeAdmin_whenCreateGroupMethod_thenOk() throws Exception {

        Group createGroup= new Group(1,"TEST");


        when(groupService.createGroup(createGroup)).thenReturn(createGroup);


        // Perform the request
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/ruru-crafts/group/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createGroup)))
                .andExpect(status().isOk())
                .andDo(print());


    }

    @Test
    @WithMockUser(username = "customer@test.com",authorities = "SCOPE_CUSTOMER")
    void givenUserIsNotGrantedWithScopeAdmin_whenCreateGroupMethod_thenUnauthorized() throws Exception {
        Group createGroup= new Group(1,"TEST");


        when(groupService.createGroup(createGroup)).thenReturn(createGroup);


        // Perform the request
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/ruru-crafts/group/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createGroup)))
                .andExpect(status().isUnauthorized())
                .andDo(print());


    }

    @Test
    @WithMockUser(username = "customer@test.com",authorities = "SCOPE_CUSTOMER",password = "password")
    void givenUserIsNotGrantedWithScopeAdmin_whenDeleteGroupByIdMethod_thenUnauthorized() throws Exception {
        Integer groupId=1;
        LocalDateTime currentDate = LocalDateTime.now();
        Date convertedDate = Date.from(currentDate.atZone(ZoneId.systemDefault()).toInstant());
        List<Group> groups=new ArrayList<>();
        Group currentGroup= new Group(1,"CREATE_GROUP");
        Group deletedGroup= new Group(1,"UPDATE_GROUP",convertedDate);

        when(groupService.getGroupById(groupId)).thenReturn(currentGroup);
        when(groupService.deleteGroup(groupId)).thenReturn(true);


        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/ruru-crafts/group/delete/{id}", groupId))
                .andExpect(status().isUnauthorized())
                .andDo(print());


    }

    @Test
    @WithMockUser(username = "admin@test.com",authorities = "SCOPE_ADMIN")
    void givenUserIsGrantedWithScopeAdmin_whenDeleteGroupByIdMethod_thenOk() throws Exception {
        Integer groupId=1;
        LocalDateTime currentDate = LocalDateTime.now();
        Date convertedDate = Date.from(currentDate.atZone(ZoneId.systemDefault()).toInstant());
        List<Group> groups=new ArrayList<>();
        Group currentGroup= new Group(1,"CREATE_GROUP");
        Group deletedGroup= new Group(1,"UPDATE_GROUP",convertedDate);

        when(groupService.getGroupById(groupId)).thenReturn(currentGroup);
        when(groupService.deleteGroup(groupId)).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/ruru-crafts/group/delete/{id}", groupId))
                .andExpect(status().isOk())
                .andDo(print());


    }
}
