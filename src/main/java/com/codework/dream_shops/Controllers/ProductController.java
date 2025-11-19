package com.codework.dream_shops.Controllers;

import com.codework.dream_shops.DTO.ProductDTO;
import com.codework.dream_shops.Exceptions.ResourceNotFoundException;
import com.codework.dream_shops.Models.Product;
import com.codework.dream_shops.Requests.AddProductRequest;
import com.codework.dream_shops.Requests.ProductUpdateRequest;
import com.codework.dream_shops.Response.ApiResponse;
import com.codework.dream_shops.service.product.ProductService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("${api_prefix}/products")
@NoArgsConstructor
@AllArgsConstructor
public class ProductController {
    @Autowired
    public ProductService productService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse>getAllProducts(){
        List<Product>products=productService.getAllProducts();
        List<ProductDTO>convertedProducts=productService.getConvertedProducts(products);
        return ResponseEntity.ok(new ApiResponse("Success",convertedProducts));
    }

    @GetMapping("/products/{productId}/product")
    public ResponseEntity<ApiResponse>getProductsById(@PathVariable Long productId){
        try {
            Product product=productService.getProductsById(productId);
            ProductDTO productDto=productService.convertToDTO(product);
            return ResponseEntity.ok(new ApiResponse("Success",productDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse>addProduct(@RequestBody AddProductRequest product){
        try {
            Product theProduct=productService.addProduct(product);
            return ResponseEntity.ok(new ApiResponse("Add Product Success!",theProduct));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(
                    new ApiResponse(e.getMessage(),null));
        }
    }

    @PutMapping("/product/{productId}/update")
    public ResponseEntity<ApiResponse>updateProduct(@RequestBody ProductUpdateRequest request,
                                                    @PathVariable  Long productId){
        try {
            Product product=productService.updateProduct(request,productId);
            return ResponseEntity.ok(new ApiResponse("Update Product success!",product));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @DeleteMapping("/product/{productId}/delete")
    public ResponseEntity<ApiResponse>deleteProduct(@PathVariable Long productId){
        try {
            productService.deleteProductById(productId);
            return ResponseEntity.ok(new ApiResponse("Delete Product success!",null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/product/by/brand-and-name")
    public ResponseEntity<ApiResponse>getProductsByBrandAndName(@RequestParam String brandName,
                                                                @RequestParam String productName){
        try {
            List<Product>products=productService.getProductsByBrandAndName(brandName,productName);
            List<ProductDTO>productDTOS=productService.getConvertedProducts(products);
            if(products.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No Products found",null));
            }
            return ResponseEntity.ok(new ApiResponse("Success",productDTOS));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("product/by/category-and-brand")
    public ResponseEntity<ApiResponse>getProductsByBrandAndCategory(@RequestParam String category,
                                                                @RequestParam String brand){
        try {
            List<Product>products=productService.getProductsByCategoryAndBrand(category,brand);
            if(products.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No Products found",null));
            }
            return ResponseEntity.ok(new ApiResponse("Success",products));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("product/{name}/products")
    public ResponseEntity<ApiResponse>getProductsByName(@PathVariable String name) {
        try {
            List<Product> products = productService.getProductsByName(name);
            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No Products found", null));
            }
            return ResponseEntity.ok(new ApiResponse("Success", products));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("product_brand/{brand}/products")
    public ResponseEntity<ApiResponse>getProductsByBrand(@PathVariable String brand) {
        try {
            List<Product> products = productService.getProductsByBrand(brand);
            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No Products found", null));
            }
            return ResponseEntity.ok(new ApiResponse("Success", products));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("product_category/{category}/products")
    public ResponseEntity<ApiResponse>getProductsByCategory(@PathVariable String category) {
        try {
            List<Product> products = productService.getProductsByCategory(category);
            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No Products found", null));
            }
            return ResponseEntity.ok(new ApiResponse("Success", products));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("product/count/by-brand/and-name")
    public ResponseEntity<ApiResponse>countProductsByBrandAndName(@RequestParam String brand,
                                                                  @RequestParam String name) {
        try {
            var productCount = productService.countProductsByBrandAndName(brand,name);
            return ResponseEntity.ok(new ApiResponse("Products Count", productCount));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }


}
