package com.ruth.rurucraftsecommerce.permissions;

import com.ruth.rurucraftsecommerce.common.BaseEntity;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

@Entity
@Table(name = "permissions")
public class Permission  extends BaseEntity {

    @Column(nullable = false,unique = true)
    private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
