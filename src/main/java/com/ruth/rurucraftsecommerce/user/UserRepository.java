package com.ruth.rurucraftsecommerce.user;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {


    @Query("SELECT DISTINCT user FROM User user " +
            "LEFT JOIN FETCH user.userGroups AS userGroups " +
            "LEFT JOIN FETCH userGroups.group AS group " +
            "LEFT JOIN FETCH group.groupPermissions AS groupPermissions " +
            "LEFT JOIN FETCH groupPermissions.permission AS permission " +
            "WHERE user.email = :email")
    Optional<User> findByEmail(@Param("email") String email);


}
