package com.example.projet.web.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.projet.web.Models.User;



public interface IUserRepository extends JpaRepository<User,Long>{
    User findByEmail(String email);
    @Modifying
    @Query("UPDATE User u SET u.password = :newPassword WHERE u.id = :id")
    void updatePassword(@Param("id") Long id, @Param("newPassword") String newPassword);
}
