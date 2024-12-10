package com.example.projet.web.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.projet.web.Models.Category;

public interface ICategoryRepository extends JpaRepository<Category,Long> {

    Category save(Long idCat);

   

}
