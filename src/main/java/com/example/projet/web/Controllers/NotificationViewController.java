package com.example.projet.web.Controllers;

import com.example.projet.web.Models.Notification;
import com.example.projet.web.Services.INotificationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.AllArgsConstructor;

import java.util.List;

@Controller
@AllArgsConstructor
public class NotificationViewController {

    private final INotificationService inotificationService;

    @GetMapping("/notifications")
    public String getNotificationsPage(Model model) {
        // Récupérer toutes les notifications depuis le service
        List<Notification> notifications = inotificationService.getAllNotification();
        model.addAttribute("notifications", notifications); 
        return "notificationsList"; 
    }
}
