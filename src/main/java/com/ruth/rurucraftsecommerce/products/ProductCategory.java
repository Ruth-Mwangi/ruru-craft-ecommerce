package com.ruth.rurucraftsecommerce.products;

import com.ruth.rurucraftsecommerce.common.BaseEntity;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Entity
@Table(name = "product_categories")

public class ProductCategory extends BaseEntity {

    private String name;

    public ProductCategory() {
    }

    public ProductCategory(ProductCategory productCategory) {
        this.setId(productCategory.getId());
        this.setCreatedAt(productCategory.getCreatedAt());
        this.setDeletedAt(productCategory.getDeletedAt());
        this.name = productCategory.getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
