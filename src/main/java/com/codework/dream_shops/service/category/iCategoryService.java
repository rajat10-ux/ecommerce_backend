package com.codework.dream_shops.service.category;

import com.codework.dream_shops.Models.Category;

import java.util.List;

public interface iCategoryService {
    Category getCategoryById(Long id);
    Category getCategoryByName(String name);
    List<Category>getAllCategories();
    Category addCategory(Category category);
    Category updateCategory(Category category,Long id);
    void deleteCategoryById(Long id);
}
