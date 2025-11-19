package com.codework.dream_shops.service.product;

import com.codework.dream_shops.DTO.ProductDTO;
import com.codework.dream_shops.Models.Product;
import com.codework.dream_shops.Requests.AddProductRequest;
import com.codework.dream_shops.Requests.ProductUpdateRequest;

import java.util.List;

public interface iProductService {

    Product addProduct(AddProductRequest request);
    Product getProductsById(Long id);
    void deleteProductById(Long id);
    Product updateProduct(ProductUpdateRequest request, Long productId);
    List<Product>getAllProducts();
    List<Product>getProductsByCategory(String category);
    List<Product>getProductsByBrand(String brand);
    List<Product>getProductsByCategoryAndBrand(String category,
                                               String brand);
    List<Product>getProductsByName(String name);
    List<Product>getProductsByBrandAndName(String category,String name);
    Long countProductsByBrandAndName(String brand, String name);

    List<ProductDTO>getConvertedProducts(List<Product> products);

    ProductDTO convertToDTO(Product product);
}
