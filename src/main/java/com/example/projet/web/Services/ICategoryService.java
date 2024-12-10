package com.example.projet.web.Services;

import java.util.List;

import com.example.projet.web.Models.Category;

public interface ICategoryService {
    public Category addCategory(Category category);
    public Category updateCategory(Category category);
    public List<Category> getAllCategory();
    public Category getCategoryById(Long id);
    public void deleteCategoryById(Long id);
}
