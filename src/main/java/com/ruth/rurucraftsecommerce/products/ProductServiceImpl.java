package com.ruth.rurucraftsecommerce.products;

import com.ruth.rurucraftsecommerce.options.OptionGroup;
import com.ruth.rurucraftsecommerce.options.OptionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    @Autowired
    private ProductOptionRepository productOptionRepository;
    @Autowired
    private OptionServiceImpl optionService;

    @Autowired
    private MessageSource messageSource;

    Locale locale = LocaleContextHolder.getLocale();

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public ProductCategory createProductCategory(ProductCategory productCategory) {
        return productCategoryRepository.save(productCategory);
    }

    @Override
    public ProductOption createProductOption(ProductOption productOption) {
        return productOptionRepository.save(productOption);
    }

    @Override
    public Product getProductById(Integer id) {
        return productRepository.findById(id).orElseThrow(()->new NoSuchElementException(messageSource.getMessage("get.fail.message",new Object[]{Product.class.getSimpleName(), id}, locale)));
    }

    @Override
    public ProductCategory getProductCategoryById(Integer id) {
        return productCategoryRepository.findById(id).orElseThrow(()->new NoSuchElementException(messageSource.getMessage("get.fail.message",new Object[]{ProductCategory.class.getSimpleName(), id}, locale)));
    }

    @Override
    public ProductOption getProductOptionById(Integer id) {
        return productOptionRepository.findById(id).orElseThrow(()->new NoSuchElementException(messageSource.getMessage("get.fail.message",new Object[]{ProductOption.class.getSimpleName(), id}, locale)));
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAllByDeletedAtIsNull();
    }

    @Override
    public List<ProductCategory> getAllProductCategory() {
        return productCategoryRepository.findAllByDeletedAtIsNull();
    }

    @Override
    public List<ProductOption> getAllProductOption() {
        return productOptionRepository.findAllByDeletedAtIsNull();
    }

    @Override
    public Product updateProduct(Integer id, Product product) {
        Product retrievedProduct=getProductById(id);
        retrievedProduct=new Product(product);

        return productRepository.save(retrievedProduct);
    }

    @Override
    public ProductCategory updateProductCategory(Integer id, ProductCategory productCategory) {
        ProductCategory retrievedProductCategory=getProductCategoryById(id);
        retrievedProductCategory=

                new ProductCategory(productCategory);

        return productCategoryRepository.save(retrievedProductCategory);
    }

    @Override
    public ProductOption updateProductOption(Integer id, ProductOption productOption) {
        ProductOption retrievedProductOption=getProductOptionById(id);
        retrievedProductOption=new ProductOption(productOption);

        return productOptionRepository.save(retrievedProductOption);
    }

    @Override
    public boolean deleteProduct(Integer id) {
        Product retrievedProduct=getProductById(id);
        LocalDateTime currentDate = LocalDateTime.now();
        Date convertedDate = Date.from(currentDate.atZone(ZoneId.systemDefault()).toInstant());
        retrievedProduct.setDeletedAt(convertedDate);
        productRepository.save(retrievedProduct);


        return true;
    }

    @Override
    public boolean deleteProductCategory(Integer id) {
        ProductCategory retrievedProductCategory=getProductCategoryById(id);
        LocalDateTime currentDate = LocalDateTime.now();
        Date convertedDate = Date.from(currentDate.atZone(ZoneId.systemDefault()).toInstant());
        retrievedProductCategory.setDeletedAt(convertedDate);
        productCategoryRepository.save(retrievedProductCategory);

        return true;
    }

    @Override
    public boolean deleteProductOption(Integer id) {
        ProductOption retrievedProductOption=getProductOptionById(id);
        Product retrievedProduct=getProductById(retrievedProductOption.getProduct().getId());
        productOptionRepository.delete(retrievedProductOption);


        return productOptionRepository.existsById(id);
    }
}
