package com.ruth.rurucraftsecommerce.permissions;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepository extends JpaRepository<Permission,Integer> {

    List<Permission> findAllByDeletedAtIsNull();

}
