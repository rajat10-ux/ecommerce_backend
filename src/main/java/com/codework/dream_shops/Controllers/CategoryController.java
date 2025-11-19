package com.codework.dream_shops.Controllers;

import com.codework.dream_shops.Exceptions.AlreadyExistsException;
import com.codework.dream_shops.Exceptions.ResourceNotFoundException;
import com.codework.dream_shops.Models.Category;
import com.codework.dream_shops.Response.ApiResponse;
import com.codework.dream_shops.service.category.CategoryService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("${api_prefix}/category")
@AllArgsConstructor
@NoArgsConstructor
public class CategoryController {
    @Autowired
    private  CategoryService categoryService;

    @GetMapping("/all")
    private ResponseEntity<ApiResponse>getAllCategories(){
        try {
            List<Category>categories=categoryService.getAllCategories();
            return ResponseEntity.ok(new ApiResponse("Found!",categories));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Error",
                    INTERNAL_SERVER_ERROR));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse>addCategory(@RequestBody Category name){
        try {
            Category theCategory=categoryService.addCategory(name);
            return ResponseEntity.ok(new ApiResponse("Success",theCategory));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT)
                    .body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/category/{id}/category")
    public ResponseEntity<ApiResponse>getCategoryById(@PathVariable Long id){
        try {
            Category theCategory = categoryService.getCategoryById(id);
            return ResponseEntity.ok(new ApiResponse("Found",theCategory));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("category/{name}/category")
    public ResponseEntity<ApiResponse>getCategoryByName(@PathVariable String name){
        try {
            Category theCategory = categoryService.getCategoryByName(name);
            return ResponseEntity.ok(new ApiResponse("Found",theCategory));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(),null));
        }
    }

    @DeleteMapping("category/{id}/delete")
    public ResponseEntity<ApiResponse>getCategoryByName(@PathVariable Long id){
        try {
            categoryService.getCategoryById(id);
            return ResponseEntity.ok(new ApiResponse("Deleted Successfully",null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PutMapping("/category/{id}/update")
    public ResponseEntity<ApiResponse>updateCategory(@PathVariable Long id,@RequestBody Category category){
        try {
            Category updateCategory = categoryService.updateCategory(category,id);
            return ResponseEntity.ok(new ApiResponse("Update Success!",updateCategory));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(),null));
        }
    }

}
