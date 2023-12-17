package com.ruth.rurucraftsecommerce.group;

import com.ruth.rurucraftsecommerce.common.BaseEntity;
import com.ruth.rurucraftsecommerce.permissions.Permission;
import com.ruth.rurucraftsecommerce.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "group_permissions")
public class GroupPermission extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    @ManyToOne
    @JoinColumn(name = "permission_id", nullable = false)
    private Permission permission;

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }
}
