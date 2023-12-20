package com.ruth.rurucraftsecommerce.products;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruth.rurucraftsecommerce.common.BaseEntity;
import com.ruth.rurucraftsecommerce.options.Option;
import com.ruth.rurucraftsecommerce.options.OptionGroup;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="product_options")
public class ProductOption extends BaseEntity {

    private Integer stock;


    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private BigDecimal priceIncrement;

    @Transient
    @ElementCollection
    private List<ProductAttribute> attributeList=new ArrayList<>();

    @Column(columnDefinition = "json")
    private String attributes;

    public ProductOption() {
    }

    public ProductOption(ProductOption productOption) {
        this.setId(productOption.getId());
        this.setCreatedAt(productOption.getCreatedAt());
        this.setDeletedAt(productOption.getDeletedAt());
        this.stock = productOption.getStock();
        this.product = productOption.product;
        this.priceIncrement = productOption.getPriceIncrement();
        this.attributeList = productOption.getAttributeList();
        this.attributes = productOption.getAttributes();
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public BigDecimal getPriceIncrement() {
        return priceIncrement;
    }

    public void setPriceIncrement(BigDecimal priceIncrement) {
        this.priceIncrement = priceIncrement;
    }

    public List<ProductAttribute> getAttributeList() {
        return attributeList;
    }

    public void setAttributeList(List<ProductAttribute> attributeList) {
        this.attributeList = attributeList;
    }

    public String getAttributes() {

        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    @PostLoad
    private void onLoad() {
        // Deserialize JSON string to List<ProductAttribute>
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            this.attributeList = objectMapper.readValue(attributes, objectMapper.getTypeFactory().constructCollectionType(List.class, ProductAttribute.class));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @PrePersist
    @PreUpdate
    private void onSave() {
        // Serialize List<ProductAttribute> to JSON string
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            this.attributes = objectMapper.writeValueAsString(attributeList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
