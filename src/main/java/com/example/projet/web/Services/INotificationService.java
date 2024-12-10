package com.example.projet.web.Services;

import java.util.List;

import com.example.projet.web.Models.Notification;

public interface INotificationService {

    public Notification addNotification(Notification notification);
    public List<Notification> getAllNotification();
    public void deleteNotificationById(Long idnotification);
}
