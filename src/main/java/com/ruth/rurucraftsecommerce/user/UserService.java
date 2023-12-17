package com.ruth.rurucraftsecommerce.user;

import java.util.List;

public interface UserService {

    UserDTO.ViewUserDTO getUserById(int id);
    List<UserDTO.ViewUserDTO> getAllUsers();
    UserDTO.ViewUserDTO updateUserDetails(int id,UserDTO.UserDetailsUpdateDTO userDetailsUpdateDTO);


}
