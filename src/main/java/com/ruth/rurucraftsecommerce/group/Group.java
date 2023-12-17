package com.ruth.rurucraftsecommerce.group;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ruth.rurucraftsecommerce.common.BaseEntity;
import com.ruth.rurucraftsecommerce.permissions.UserPermission;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "`groups`")
public class Group  extends BaseEntity {

    @Column(nullable = false,unique = true)
    private String name;


    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<GroupPermission> groupPermissions;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<GroupPermission> getGroupPermissions() {
        return groupPermissions;
    }

    public void setGroupPermissions(Set<GroupPermission> groupPermissions) {
        this.groupPermissions = groupPermissions;
    }
}
