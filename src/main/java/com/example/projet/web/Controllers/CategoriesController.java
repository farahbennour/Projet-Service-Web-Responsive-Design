package com.example.projet.web.Controllers;

import com.example.projet.web.Models.Category;
import com.example.projet.web.Services.ICategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@AllArgsConstructor
public class CategoriesController {
    private final ICategoryService iCategoryService;
 

    @GetMapping("/category/getById")
    @ResponseBody
    public Category getById(@RequestParam("id") Long id) {
        Category category = iCategoryService.getCategoryById(id);
        return category;
    }

    @GetMapping("/categoryForm")
    public String showCategoryForm(Model model) {
        model.addAttribute("category", new Category()); 
        model.addAttribute("categories", iCategoryService.getAllCategory()); 
        return "Category_Form";
    }

    @PostMapping("/addCategory")
    public String addCategory(@ModelAttribute Category category, Model model) {
        iCategoryService.addCategory(category); 
        model.addAttribute("category", new Category()); 
        model.addAttribute("categories", iCategoryService.getAllCategory()); 
        return "redirect:/categoryForm?success"; 
    }

    @PostMapping("/updateCategory")
    public String updateCategory(@ModelAttribute Category category, Model model) {
        Category existingCategory = iCategoryService.getCategoryById(category.getId());
        if (existingCategory != null) {
            existingCategory.setNamecat(category.getNamecat());
            iCategoryService.updateCategory(existingCategory); 
            return "redirect:/categoryForm?updated"; 
        }
        return "redirect:/categoryForm?error";
    }

    @PostMapping("/deleteCategory")
    public String deleteCategory(@ModelAttribute Category category, Model model) {
       
        iCategoryService.deleteCategoryById(category.getId());
        return "redirect:/categoryForm?deleted"; 
    }
}
