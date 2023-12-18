package com.ruth.rurucraftsecommerce.permissions;

import com.ruth.rurucraftsecommerce.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;


@Service
public class PermissionServiceImpl implements PermissionService{

    @Autowired
    PermissionRepository permissionRepository;

    @Override
    public Permission createPermission(Permission permission) {
        Permission savedPermission = permissionRepository.save(permission);

        if (savedPermission == null) {
            throw new RuntimeException("Failed to save permission");
        }

        return savedPermission;
    }

    @Override
    public Permission getPermissionById(Integer id) {

        return permissionRepository.findById(id).orElseThrow(()->new NoSuchElementException("No such element with id : "+id));
    }

    @Override
    public List<Permission> getAllPermissions() {
        return permissionRepository.findAllByDeletedAtIsNull();
    }

    @Override
    public Permission updatePermission(Integer id, Permission permission) {
        Permission retrievePermission=permissionRepository.findById(id).orElseThrow(()->new NoSuchElementException("No such element with id : "+id));
        retrievePermission.setName(permission.getName());

        Permission updatedPermission=permissionRepository.save(retrievePermission);
        return updatedPermission;
    }

    @Override
    public boolean deletePermission(Integer id) {
        Permission retrievePermission=permissionRepository.findById(id).orElseThrow(()->new NoSuchElementException("No such element with id : "+id));
        LocalDateTime currentDate = LocalDateTime.now();
        Date convertedDate = Date.from(currentDate.atZone(ZoneId.systemDefault()).toInstant());

        retrievePermission.setDeletedAt(convertedDate);

        Permission updatedPermission=permissionRepository.save(retrievePermission);
        return true;    }
}
