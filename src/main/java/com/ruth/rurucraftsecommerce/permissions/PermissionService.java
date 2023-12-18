package com.ruth.rurucraftsecommerce.permissions;


import java.util.List;

public interface PermissionService {

    Permission createPermission(Permission permission);
    Permission getPermissionById(Integer id);
    List<Permission> getAllPermissions();
    Permission updatePermission(Integer id,Permission permission);
    boolean deletePermission(Integer id);
}
