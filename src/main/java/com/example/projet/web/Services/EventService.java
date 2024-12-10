package com.example.projet.web.Services;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.projet.web.Models.Event;
import com.example.projet.web.Repositories.IEventRepository;
import com.example.projet.web.Repositories.NotificationRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EventService implements IEventService {
    private IEventRepository iEventRepository; 
    private NotificationRepository iNotificationRepository; 
        
    @Override
    public Event addEvent(Event event) {
        return iEventRepository.save(event);
    }

    @Override
    public Event getEventById(Long id) {
    return iEventRepository.findByIdWithCategory(id).orElse(null);
    }
    @Transactional
    @Override
    public void deleteEventById(Long idEvent) {
        iNotificationRepository.deleteByEventId(idEvent);
        iEventRepository.deleteById(idEvent);
       
    }

    @Override
    public Event updateEvent(Event event) {
        return iEventRepository.save(event);
    }

    @Override
    public List<Event> getAllEvents() {
        return iEventRepository.findAll();
    }
    @Override
    public List<Event> findEventsByLocation(String lieuEvent) {
        return iEventRepository.findEventsByLocation(lieuEvent); 
    }
   }


