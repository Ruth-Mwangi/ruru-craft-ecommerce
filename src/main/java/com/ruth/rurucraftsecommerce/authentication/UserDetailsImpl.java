package com.ruth.rurucraftsecommerce.authentication;

import com.ruth.rurucraftsecommerce.user.User;
import com.ruth.rurucraftsecommerce.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserDetailsImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthUser user = userRepository
                .findByEmail(username)
                .map(AuthUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return user;

    }

    public User saveUser(User user){
        return userRepository.save(user);
    }


}
