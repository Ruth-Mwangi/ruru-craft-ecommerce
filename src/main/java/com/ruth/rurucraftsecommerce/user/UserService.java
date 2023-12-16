package com.ruth.rurucraftsecommerce.user;

import java.util.List;

public interface UserService {

    UserDTO.ViewUserDTO getUserById(int id);
    List<UserDTO.ViewUserDTO> getAllUsers();
    UserDTO.ViewUserDTO enableOrDisableUser(int id,UserDTO.UserEnableAccountDTO enableAccountDTO);
    UserDTO.ViewUserDTO unlockOrLockAccount(int id,UserDTO.UserUnlockAccountDTO unlockAccountDTO);
    UserDTO.ViewUserDTO updateUserDetails(int id,UserDTO.UserDetailsUpdateDTO userDetailsUpdateDTO);


}
