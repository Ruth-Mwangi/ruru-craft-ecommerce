package com.ruth.rurucraftsecommerce.user;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {


    @Query("SELECT DISTINCT user FROM User user " +
            "LEFT JOIN FETCH user.userPermissions AS userPermissions " +
            "LEFT JOIN FETCH userPermissions.permission AS permission " +
            "WHERE user.username = :username")
    Optional<User> findByUsername(@Param("username") String username);
}
