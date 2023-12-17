package com.ruth.rurucraftsecommerce.authentication;

import com.ruth.rurucraftsecommerce.permissions.Permission;
import com.ruth.rurucraftsecommerce.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class AuthUser extends User  implements UserDetails{
    private final User user;

    public AuthUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<String> roles = user.getUserGroups().stream()
                .map(userGroup -> userGroup.getGroup().getName())
                .collect(Collectors.toSet());

        Set<String> authorities = user.getUserGroups().stream()
                .flatMap(userGroup -> userGroup.getGroup().getGroupPermissions().stream())
                .map(permission->permission.getPermission().getName())
                .collect(Collectors.toSet());

        authorities.addAll(roles);


        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public Integer getId() {
        return user.getId();
    }
}
