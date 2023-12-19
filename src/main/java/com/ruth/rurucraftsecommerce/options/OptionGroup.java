package com.ruth.rurucraftsecommerce.options;

import com.ruth.rurucraftsecommerce.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.Date;

@Entity
@Table(name = "option_groups")
public class OptionGroup extends BaseEntity {

    @Column(unique = true,nullable = false)
    private String name;

    public OptionGroup(Integer id,String name) {
        this.setId(id);
        this.name = name;
    }

    public OptionGroup() {
    }

    public OptionGroup(Integer id, String name, Date deletedAt) {
        this.setId(id);
        this.setDeletedAt(deletedAt);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
