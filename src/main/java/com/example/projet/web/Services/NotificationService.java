package com.example.projet.web.Services;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.projet.web.Models.Notification;
import com.example.projet.web.Repositories.NotificationRepository;

import lombok.AllArgsConstructor;

import java.util.List;

@Service
@AllArgsConstructor
public class NotificationService implements INotificationService{
    
    private NotificationRepository notificationRepository;  
    @Override
    public Notification addNotification(Notification notification) {
     return  notificationRepository.save(notification);
    }

   

    @Override
    public List<Notification> getAllNotification() {
        return notificationRepository.findAll();
    }

    
    @Transactional
    @Override
    public void deleteNotificationById(Long idNotification) {
        notificationRepository.deleteById(idNotification);
        
    }

    
    

   
}
