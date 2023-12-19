package com.ruth.rurucraftsecommerce.options;

import com.ruth.rurucraftsecommerce.common.BaseEntity;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "options")
public class Option extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @ManyToOne // Assuming many options belong to one group
    @JoinColumn(name = "option_group_id", nullable = false)
    private OptionGroup optionGroup;


    public Option() {
    }

    public Option(Integer id,String name, Integer optionGroupId) {
        this.setId(id);
        this.name = name;
        this.optionGroup.setId(optionGroupId);
    }

    public Option(Integer id, String name, Integer optionGroupId, Date deletedAt) {
        this.setId(id);
        this.setDeletedAt(deletedAt);
        this.name = name;
        this.optionGroup.setId(optionGroupId);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OptionGroup getOptionGroup() {
        return optionGroup;
    }

    public void setOptionGroup(OptionGroup optionGroup) {
        this.optionGroup = optionGroup;
    }
}
