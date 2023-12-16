package com.ruth.rurucraftsecommerce.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDTO.ViewUserDTO getUserById(int id) {
         User retrivedUser=userRepository.findById(id).orElseThrow(()->new NoSuchElementException("User not found with ID: " + id));
         return UserMapper.mapToUserDto(retrivedUser);

    }

    @Override
    public List<UserDTO.ViewUserDTO> getAllUsers() {
        List<User> users=userRepository.findAll();
        return users.stream().map(UserMapper::mapToUserDto).toList();
    }

    @Override
    public UserDTO.ViewUserDTO enableOrDisableUser(int id,UserDTO.UserEnableAccountDTO enableAccountDTO) {
        User retrivedUser=userRepository.findById(id).orElseThrow(()->new NoSuchElementException("User not found with ID: " + id));
        retrivedUser.setEnabled(enableAccountDTO.isEnabled());
        User updatedUser=userRepository.save(retrivedUser);

        return UserMapper.mapToUserDto(updatedUser);
    }

    @Override
    public UserDTO.ViewUserDTO unlockOrLockAccount(int id,UserDTO.UserUnlockAccountDTO unlockAccountDTO) {
        User retrivedUser=userRepository.findById(id).orElseThrow(()->new NoSuchElementException("User not found with ID: " + id));
        retrivedUser.setAccountNonLocked(unlockAccountDTO.isAccountNonLocked());
        User updatedUser=userRepository.save(retrivedUser);

        return UserMapper.mapToUserDto(updatedUser);
    }

    @Override
    public UserDTO.ViewUserDTO updateUserDetails(int id,UserDTO.UserDetailsUpdateDTO userDetailsUpdateDTO) {
        User retrivedUser=userRepository.findById(id).orElseThrow(()->new NoSuchElementException("User not found with ID: " + id));
        retrivedUser.setUsername(userDetailsUpdateDTO.getUsername());
        retrivedUser.setFirstName(userDetailsUpdateDTO.getFirstName());
        retrivedUser.setLastName(userDetailsUpdateDTO.getLastName());
        retrivedUser.setPhoneNumber(userDetailsUpdateDTO.getPhoneNumber());
        User updatedUser=userRepository.save(retrivedUser);
        return UserMapper.mapToUserDto(updatedUser);

    }
}
