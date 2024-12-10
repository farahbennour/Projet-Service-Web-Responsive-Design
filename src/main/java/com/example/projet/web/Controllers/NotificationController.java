package com.example.projet.web.Controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.projet.web.Models.Notification;
import com.example.projet.web.Services.INotificationService;

import lombok.AllArgsConstructor;
@RestController
@AllArgsConstructor
public class NotificationController {
    
   private INotificationService inotificationService;
    @PostMapping("/addNotification")
    public Notification addNotification(@RequestBody Notification notification) {
        return inotificationService.addNotification(notification);
    }
    
    @GetMapping("/getAllNotification")
    public List<Notification> getAllNotifications() {
        return inotificationService.getAllNotification();
    }
   
    @DeleteMapping("/deleteNotificatonById/{idnotification}")
    public void deleteNotificationById(@PathVariable Long idnotification) {
        inotificationService.deleteNotificationById(idnotification);
    }

    
}
