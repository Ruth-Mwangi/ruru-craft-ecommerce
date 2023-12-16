package com.ruth.rurucraftsecommerce.user;

public class UserMapper {
    // Convert User JPA Entity into UserDto
    public static UserDTO.ViewUserDTO mapToUserDto(User user){
        return new UserDTO.ViewUserDTO(
                user
        );
    }

    // Convert UserDto into User JPA Entity
    public static User mapToUser(UserDTO.ViewUserDTO viewUserDTO){
        return new User(
                viewUserDTO.getId(),
                viewUserDTO.getUsername(),
                viewUserDTO.isAccountNonLocked(),
                viewUserDTO.isEnabled(),
                viewUserDTO.getFirstName(),
                viewUserDTO.getLastName(),
                viewUserDTO.getPhoneNumber()
        );
    }
}
