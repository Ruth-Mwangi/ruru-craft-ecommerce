package com.ruth.rurucraftsecommerce.products;

import com.ruth.rurucraftsecommerce.group.Group;
import com.ruth.rurucraftsecommerce.options.OptionGroup;
import com.ruth.rurucraftsecommerce.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;

@Tag(name = "6. Products & Product Categories & Product OPtions ", description = "Interact with products, product categories and product options in a system. You can create new ones, modify existing ones, list, or remove ones that are no longer necessary.")

@RestController
@RequestMapping("/ruru-crafts")
public class ProductController {

    public Locale locale = LocaleContextHolder.getLocale();

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private ProductServiceImpl productService;

    @Operation(summary = "This endpoint creates a product categories and requires user to be authenticated")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PostMapping("/product-category/create")
    public ResponseEntity<?> createProductCategory(@RequestBody ProductCategory productCategory){
        try {
            ProductCategory createdProductCategory=productService.createProductCategory(productCategory);
            Response response=new Response(HttpStatus.OK.value(),
                    messageSource.getMessage("create.success.message",new Object[]{createdProductCategory.getClass().getSimpleName()},locale),createdProductCategory );
            return ResponseEntity.ok(response);

        }catch (NoSuchElementException e){
            Response response= new Response(HttpStatus.NOT_FOUND.value(), e.getMessage() );
            return ResponseEntity.badRequest().body(response);
        }
    }

    @Operation(summary = "This endpoint creates a product and requires user to be authenticated")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PostMapping("/product/create")
    public ResponseEntity<?> createProduct(@RequestBody Product product){
        try {
            Product createProduct=productService.createProduct(product);
            Response response=new Response(HttpStatus.OK.value(),
                    messageSource.getMessage("create.success.message",new Object[]{createProduct.getClass().getSimpleName()},locale),createProduct );
            return ResponseEntity.ok(response);

        }catch (NoSuchElementException e){
            Response response= new Response(HttpStatus.NOT_FOUND.value(), e.getMessage() );
            return ResponseEntity.badRequest().body(response);
        }
    }

    @Operation(summary = "This endpoint creates a product options and requires user to be authenticated")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PostMapping("/product-options/create")
    public ResponseEntity<?> createProductOptions(@RequestBody ProductOption productOption){
        try {
            ProductOption createdProductOption=productService.createProductOption(productOption);
            Response response=new Response(HttpStatus.OK.value(),
                    messageSource.getMessage("create.success.message",new Object[]{createdProductOption.getClass().getSimpleName()},locale),createdProductOption );
            return ResponseEntity.ok(response);

        }catch (NoSuchElementException e){
            Response response= new Response(HttpStatus.NOT_FOUND.value(), e.getMessage() );
            return ResponseEntity.badRequest().body(response);
        }
    }

    @Operation(summary = "This endpoint gets all products and requires user to be authenticated")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping("/products")
    public ResponseEntity<?> getAllProducts(){
        try {
            List<Product> products=productService.getAllProducts();
            Response response=new Response(HttpStatus.OK.value(),
                    messageSource.getMessage("get.success.message",new Object[]{Product.class.getSimpleName()},locale),products );
            return ResponseEntity.ok(response);

        }catch (NoSuchElementException e){
            Response response= new Response(HttpStatus.NOT_FOUND.value(), e.getMessage() );
            return ResponseEntity.badRequest().body(response);
        }
    }

    @Operation(summary = "This endpoint gets all products and requires user to be authenticated")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping("/product-categories")
    public ResponseEntity<?> getAllProductCategories(){
        try {
            List<ProductCategory> productCategories=productService.getAllProductCategory();
            Response response=new Response(HttpStatus.OK.value(),
                    messageSource.getMessage("get.success.message",new Object[]{productCategories.getClass().getClass().getSimpleName()},locale),productCategories );
            return ResponseEntity.ok(response);

        }catch (NoSuchElementException e){
            Response response= new Response(HttpStatus.NOT_FOUND.value(), e.getMessage() );
            return ResponseEntity.badRequest().body(response);
        }
    }

    @Operation(summary = "This endpoint gets a product by id and requires user to be authenticated")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping("/product/{id}")
    public ResponseEntity<?> getProductById(@PathVariable("id") Integer id){
        try {
            Product products=productService.getProductById(id);
            Response response=new Response(HttpStatus.OK.value(),
                    messageSource.getMessage("get.success.message",new Object[]{Product.class.getSimpleName()},locale),products );
            return ResponseEntity.ok(response);

        }catch (NoSuchElementException e){
            Response response= new Response(HttpStatus.NOT_FOUND.value(), e.getMessage() );
            return ResponseEntity.badRequest().body(response);
        }
    }

    @Operation(summary = "This endpoint deletes a product category and requires user to be authenticated")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @DeleteMapping("/product-category/delete/{id}")
    public ResponseEntity<?>deleteProductCategoryById(@PathVariable("id") Integer id){
        try {
            boolean deletedData=productService.deleteProductCategory(id);
            Response response=new Response(HttpStatus.OK.value(),
                    messageSource.getMessage("delete.success.message",new Object[]{ProductCategory.class.getSimpleName()},locale) );
            return ResponseEntity.ok(response);

        }catch (NoSuchElementException e){
            Response response= new Response(HttpStatus.NOT_FOUND.value(), e.getMessage() );
            return ResponseEntity.badRequest().body(response);
        }
    }
    @Operation(summary = "This endpoint deletes a product options and requires user to be authenticated")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @DeleteMapping("/product-option/delete/{id}")
    public ResponseEntity<?>deleteProductOptionById(@PathVariable("id") Integer id){
        try {
            boolean deletedData=productService.deleteProductOption(id);
            Response response=new Response(HttpStatus.OK.value(),
                    messageSource.getMessage("delete.success.message",new Object[]{ProductOption.class.getSimpleName()},locale) );
            return ResponseEntity.ok(response);

        }catch (NoSuchElementException e){
            Response response= new Response(HttpStatus.NOT_FOUND.value(), e.getMessage() );
            return ResponseEntity.badRequest().body(response);
        }
    }

    @Operation(summary = "This endpoint deletes a product and requires user to be authenticated")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @DeleteMapping("/product/delete/{id}")
    public ResponseEntity<?>deleteProductById(@PathVariable("id") Integer id){
        try {
            boolean deletedData=productService.deleteProduct(id);
            Response response=new Response(HttpStatus.OK.value(),
                    messageSource.getMessage("delete.success.message",new Object[]{Product.class.getSimpleName()},locale) );
            return ResponseEntity.ok(response);

        }catch (NoSuchElementException e){
            Response response= new Response(HttpStatus.NOT_FOUND.value(), e.getMessage() );
            return ResponseEntity.badRequest().body(response);
        }
    }

    @Operation(summary = "This endpoint updates a product and requires user to be authenticated")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PutMapping("/product/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable("id") Integer id,@RequestBody Product product){

        try{
            Product createdData= productService.updateProduct(id,product);
            Response response=new Response(HttpStatus.OK.value(), messageSource.getMessage("update.success.message",new Object[]{createdData.getClass().getSimpleName()},locale) ,createdData);
            return ResponseEntity.ok(response);
        }catch (AccessDeniedException e){
            Response response=new Response(HttpStatus.UNAUTHORIZED.value(), "Data not retrieved!",null);
            return ResponseEntity.badRequest().body(response);
        }catch (NoSuchElementException e){
            Response response= new Response(HttpStatus.NOT_FOUND.value(), e.getMessage() );
            return ResponseEntity.badRequest().body(response);
        }catch (Exception e){
            Response response=new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Data not retrieved!",null);
            return ResponseEntity.badRequest().body(response);
        }

    }
    @Operation(summary = "This endpoint updates a product category and requires user to be authenticated")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PutMapping("/product-category/update/{id}")
    public ResponseEntity<?> updateProductCategory(@PathVariable("id") Integer id,@RequestBody ProductCategory productCategory){

        try{
            ProductCategory createdData= productService.updateProductCategory(id,productCategory);
            Response response=new Response(HttpStatus.OK.value(), messageSource.getMessage("update.success.message",new Object[]{createdData.getClass().getSimpleName()},locale) ,createdData);
            return ResponseEntity.ok(response);
        }catch (AccessDeniedException e){
            Response response=new Response(HttpStatus.UNAUTHORIZED.value(), "Data not retrieved!",null);
            return ResponseEntity.badRequest().body(response);
        }catch (NoSuchElementException e){
            Response response= new Response(HttpStatus.NOT_FOUND.value(), e.getMessage() );
            return ResponseEntity.badRequest().body(response);
        }catch (Exception e){
            Response response=new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Data not retrieved!",null);
            return ResponseEntity.badRequest().body(response);
        }

    }

    @Operation(summary = "This endpoint updates a product option and requires user to be authenticated")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PutMapping("/product-option/update/{id}")
    public ResponseEntity<?> updateProductOption(@PathVariable("id") Integer id,@RequestBody ProductOption productOption){

        try{
            ProductOption createdData= productService.updateProductOption(id,productOption);
            Response response=new Response(HttpStatus.OK.value(), messageSource.getMessage("update.success.message",new Object[]{createdData.getClass().getSimpleName()},locale) ,createdData);
            return ResponseEntity.ok(response);
        }catch (AccessDeniedException e){
            Response response=new Response(HttpStatus.UNAUTHORIZED.value(), "Data not retrieved!",null);
            return ResponseEntity.badRequest().body(response);
        }catch (NoSuchElementException e){
            Response response= new Response(HttpStatus.NOT_FOUND.value(), e.getMessage() );
            return ResponseEntity.badRequest().body(response);
        }catch (Exception e){
            Response response=new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Data not retrieved!",null);
            return ResponseEntity.badRequest().body(response);
        }

    }

}
