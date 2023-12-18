package com.ruth.rurucraftsecommerce.permissions;

import com.ruth.rurucraftsecommerce.common.BaseEntity;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "permissions")
public class Permission  extends BaseEntity {

    @Column(nullable = false,unique = true)
    private String name;

    public Permission(Integer id,String name) {
        this.setId(id);
        this.name = name;
    }

    public Permission(Integer id, String name, Date deletedAt) {
        this.setDeletedAt(deletedAt);
        this.setId(id);
        this.name = name;
    }
    public Permission() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
