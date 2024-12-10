package com.example.projet.web.Services;

import java.util.List;

import com.example.projet.web.Models.Event;

public interface IEventService {
    Event addEvent(Event event);
    Event updateEvent(Event event);
    List<Event> getAllEvents();
    Event getEventById(Long id);
    void deleteEventById(Long id);
    public List<Event> findEventsByLocation(String lieuEvent);
}
