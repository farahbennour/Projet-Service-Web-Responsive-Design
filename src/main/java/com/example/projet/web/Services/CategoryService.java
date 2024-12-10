package com.example.projet.web.Services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.projet.web.Models.Category;
import com.example.projet.web.Models.Event;
import com.example.projet.web.Repositories.ICategoryRepository;
import com.example.projet.web.Repositories.IEventRepository;
import com.example.projet.web.Repositories.NotificationRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CategoryService implements ICategoryService{
    private ICategoryRepository iCategoryRepository;
    private NotificationRepository notificationRepository;
    private IEventRepository eventRepository;
    @Override
    public Category addCategory(Category category) {
        return iCategoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Category category) {
        return iCategoryRepository.save(category);
    }

    @Override
    public List<Category> getAllCategory() {
        return iCategoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(Long id) {
        return iCategoryRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void deleteCategoryById(Long id) {
          Category category = iCategoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Category not found"));

   
    for (Event event : category.getEvenements()) {
        notificationRepository.deleteByEventId(event.getId()); 
    }
    eventRepository.deleteAll(category.getEvenements());

    iCategoryRepository.deleteById(id);
    }
}
