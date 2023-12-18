package com.ruth.rurucraftsecommerce.group;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ruth.rurucraftsecommerce.common.BaseEntity;
import com.ruth.rurucraftsecommerce.permissions.Permission;
import com.ruth.rurucraftsecommerce.permissions.UserPermission;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "`groups`")
public class Group  extends BaseEntity {

    @Column(nullable = false,unique = true)
    private String name;


    @JsonIgnore
    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<GroupPermission> groupPermissions;

    public Group(Integer id, String name) {
        this.setId(id);
        this.name=name;
    }

    public Group(Integer id, String name,Date deletedAt) {
        this.setId(id);
        this.setDeletedAt(deletedAt);
        this.name=name;
    }

    public Group() {

    }


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

    public void setPermissions(List<Permission> permissions) {
    }
}
