package com.ruth.rurucraftsecommerce.products;

import java.util.List;

public interface ProductService {
    Product createProduct(Product product);
    ProductCategory createProductCategory(ProductCategory productCategory);
    ProductOption createProductOption(ProductOption productOption);
    Product getProductById(Integer id);
    ProductCategory getProductCategoryById(Integer id);
    ProductOption getProductOptionById(Integer id);
    List<Product> getAllProducts();
    List<ProductCategory> getAllProductCategory();
    List<ProductOption> getAllProductOption();
    Product updateProduct(Integer id,Product product);
    ProductCategory updateProductCategory(Integer id,ProductCategory productCategory);
    ProductOption updateProductOption(Integer id,ProductOption productOption);
    boolean deleteProduct(Integer id);
    boolean deleteProductCategory(Integer id);
    boolean deleteProductOption(Integer id);
}
