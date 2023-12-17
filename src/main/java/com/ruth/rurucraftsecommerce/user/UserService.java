package com.ruth.rurucraftsecommerce.user;

import org.springframework.data.relational.core.sql.In;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface UserService {

    UserDTO.ViewUserDTO getUserById(int id);
    List<UserDTO.ViewUserDTO> getAllUsers();
    UserDTO.ViewUserDTO updateUserDetails(int id,UserDTO.UserDetailsUpdateDTO userDetailsUpdateDTO);
    boolean isResourceOwner(Authentication authentication, Integer userId);

    boolean deleteUser(Integer id) throws Exception;



}
