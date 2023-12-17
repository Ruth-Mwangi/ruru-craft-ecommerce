package com.ruth.rurucraftsecommerce.permissions;

import com.ruth.rurucraftsecommerce.common.BaseEntity;
import com.ruth.rurucraftsecommerce.user.User;
import jakarta.persistence.*;

import java.util.prefs.BackingStoreException;

//@Entity
//@Table(name = "user_permissions")
public class UserPermission  extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "permission_id", nullable = false)
    private Permission permission;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }
}
