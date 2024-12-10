package com.example.projet.web.Repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.projet.web.Models.Notification;

import io.lettuce.core.dynamic.annotation.Param;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
@Modifying
@Query("DELETE FROM Notification n WHERE n.event.id = :eventId")
void deleteByEventId(@Param("eventId") Long eventId);
   
    
}
