package com.ruth.rurucraftsecommerce.products;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ruth.rurucraftsecommerce.common.BaseEntity;
import com.ruth.rurucraftsecommerce.options.OptionGroup;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "products")
public class Product extends BaseEntity {

    @Column(nullable = false,unique = true)
    private String name;
    @Column(nullable = false,unique = true)
    private String sku;
    @Column(nullable = false)
    private BigDecimal price;
    @Column(nullable = false)
    private String shortDesc;
    @Column(nullable = false)
    private String longDesc;
    private byte[] productImage;
    @ManyToOne // Assuming many products belong to one category
    @JoinColumn(name = "product_category_id", nullable = false)
    private ProductCategory productCategory;
    @Column(columnDefinition = "tinyint(1) default 0",nullable = false)
    private boolean productLive;
    @JsonManagedReference
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductOption> productOptions;

    public Product() {
    }

    public Product(Product product) {
        this.setId(product.getId());
        this.setCreatedAt(product.getCreatedAt());
        this.setDeletedAt(product.getDeletedAt());
        this.name = product.getName();
        this.sku = product.getSku();
        this.price = product.getPrice();
        this.shortDesc = product.getShortDesc();
        this.longDesc = product.getLongDesc();
        this.productImage = product.getProductImage();
        this.productCategory = product.productCategory;
        this.productLive = product.productLive;
        this.productOptions = product.productOptions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getLongDesc() {
        return longDesc;
    }

    public void setLongDesc(String longDesc) {
        this.longDesc = longDesc;
    }

    public byte[] getProductImage() {
        return productImage;
    }

    public void setProductImage(byte[] productImage) {
        this.productImage = productImage;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public boolean isProductLive() {
        return productLive;
    }

    public void setProductLive(boolean productLive) {
        this.productLive = productLive;
    }

    public List<ProductOption> getProductOptions() {
        return productOptions;
    }

    public void setProductOptions(List<ProductOption> productOptions) {
        this.productOptions = productOptions;
    }
}
