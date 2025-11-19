package com.codework.dream_shops.service.product;

import com.codework.dream_shops.DTO.ImageDTO;
import com.codework.dream_shops.DTO.ProductDTO;
import com.codework.dream_shops.Exceptions.ProductNotFoundException;
import com.codework.dream_shops.Models.Category;
import com.codework.dream_shops.Models.Image;
import com.codework.dream_shops.Models.Product;
import com.codework.dream_shops.Repository.CategoryRepository;
import com.codework.dream_shops.Repository.ImageRepository;
import com.codework.dream_shops.Repository.ProductRepository;
import com.codework.dream_shops.Requests.AddProductRequest;
import com.codework.dream_shops.Requests.ProductUpdateRequest;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class ProductService implements iProductService{
    @Autowired
    private  ProductRepository productRepository;
    @Autowired
    private  CategoryRepository categoryRepository;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private  ModelMapper modelMapper;
    @Override
    public Product addProduct(AddProductRequest request) {
        //check if the category is found in DB
        // if YES,set it as the new product category
        // if No, the save it as a new category
        //The set as new product category
        Category category= Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
                .orElseGet(()->{
                    Category newCategory=new Category(request.getCategory().getName());
                    return categoryRepository.save(newCategory);
                });
        request.setCategory(category);
        return productRepository.save(createProduct(request,category));
    }

    private Product createProduct(AddProductRequest request, Category category){
        return new Product(
                request.getName(),
                request.getBrand(),
                request.getPrice(),
                request.getInventory(),
                request.getDescription(),
                category
        );
    }

    @Override
    public Product getProductsById(Long id) {
        return productRepository.findById(id).orElseThrow(
                ()->new ProductNotFoundException("Product Not Found"));
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.findById(id).ifPresentOrElse(productRepository::delete,
                ()->{throw new ProductNotFoundException("Product Not Found");});
    }


    private Product updateExistingProduct(Product existingProduct,
                                          ProductUpdateRequest request){
       existingProduct.setName(request.getName());
       existingProduct.setBrand(request.getBrand());
       existingProduct.setPrice(request.getPrice());
       existingProduct.setInventory(request.getInventory());
       existingProduct.setDescription(request.getDescription());

       Category category=categoryRepository.findByName(request.getCategory().getName());
       existingProduct.setCategory(category);
       return existingProduct;
    }

    @Override
    public Product updateProduct(ProductUpdateRequest request, Long productId) {
        return productRepository.findById(productId)
                .map(a->updateExistingProduct(a,request))
                .map(productRepository::save)
                .orElseThrow(()->new ProductNotFoundException("Product Not Found"));
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category,brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand,name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return (long) productRepository.countByBrandAndName(brand,name);
    }

    @Override
    public List<ProductDTO>getConvertedProducts(List<Product> products){
        return products.stream().map(this::convertToDTO).toList();
    }

    @Override
    public ProductDTO convertToDTO(Product product){
        ProductDTO productDTO=modelMapper.map(product,ProductDTO.class);
        List<Image>images= imageRepository.findByProductId(product.getId());
        List<ImageDTO>imageDTOS=images.stream().map(a->modelMapper.map(a,ImageDTO.class)).toList();
        productDTO.setImages(imageDTOS);
        return productDTO;
    }
}
