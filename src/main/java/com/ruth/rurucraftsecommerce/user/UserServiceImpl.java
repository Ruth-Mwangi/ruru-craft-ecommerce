package com.ruth.rurucraftsecommerce.user;

import com.ruth.rurucraftsecommerce.authentication.AuthUser;
import com.ruth.rurucraftsecommerce.authentication.UserDetailsImpl;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsImpl userDetails;


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
    public UserDTO.ViewUserDTO updateUserDetails(int id,UserDTO.UserDetailsUpdateDTO userDetailsUpdateDTO) {
        User retrivedUser=userRepository.findById(id).orElseThrow(()->new NoSuchElementException("User not found with ID: " + id));
        retrivedUser.setEmail(userDetailsUpdateDTO.getEmail());
        retrivedUser.setFirstName(userDetailsUpdateDTO.getFirstName());
        retrivedUser.setLastName(userDetailsUpdateDTO.getLastName());
        retrivedUser.setPhoneNumber(userDetailsUpdateDTO.getPhoneNumber());
        User updatedUser=userRepository.save(retrivedUser);
        return UserMapper.mapToUserDto(updatedUser);

    }

    public  Integer getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        if (principal instanceof AuthUser) {
            return ((AuthUser) principal).getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean isResourceOwner(Authentication authentication, Integer id) {

        AuthUser authUser= (AuthUser) userDetails.loadUserByUsername(authentication.getName());
        return Objects.equals(authUser.getId(), id);
    }

    @Override
    public boolean deleteUser(Integer id) throws Exception {
        User retrivedUser=userRepository.findById(id).orElseThrow(()->new NoSuchElementException("User not found with ID: " + id));
        LocalDateTime currentDate = LocalDateTime.now();
        Date convertedDate = Date.from(currentDate.atZone(ZoneId.systemDefault()).toInstant());

        retrivedUser.setDeletedAt(convertedDate);

        try {
            User user=userRepository.save(retrivedUser);
            return true;

        }catch (Exception e){
            throw new Exception("User not deleted.");

        }

    }
}
