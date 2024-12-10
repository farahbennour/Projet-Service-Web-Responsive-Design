package com.example.projet.web.Services;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.projet.web.Models.Event;
import com.example.projet.web.Models.Registration;
import com.example.projet.web.Models.User;
import com.example.projet.web.Repositories.IEventRepository;
import com.example.projet.web.Repositories.IRegistrationRepository;
import com.example.projet.web.Repositories.IUserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RegistrationService implements IRegistrationService{
    private IRegistrationRepository iRegistrationRepository;
    private IEventRepository iEventRepository;
    private IUserRepository iUserRepository;

    @Override
    @Transactional
    public boolean unregisterUserFromEvent(Long userId, Long eventId) {
        Registration registration = iRegistrationRepository.findRegistrationByUserIdAndEventId(userId, eventId);
        iRegistrationRepository.delete(registration);
        return true;
       
    }

    @Override
    public List<Registration> getRegistrationsByUser(Long userId) {
        return iRegistrationRepository.findByUserId(userId);
    }

    @Override
    public List<Registration> getRegistrationsByEvent(Long eventId) {
        return iRegistrationRepository.findByEventId(eventId);
    }

    @Override
    public Registration registerUserForEvent(Long userId, Long eventId) {
        User user = iUserRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Event event = iEventRepository.findById(eventId).orElseThrow(() -> new RuntimeException("Event not found"));
    
        Registration registration = iRegistrationRepository.findRegistrationByUserIdAndEventId(userId, eventId);
        
        if (registration != null) {
            System.out.println("User " + userId + " is already registered for event " + eventId);
            return registration; 
        } 
        else {
            Registration newRegistration = new Registration();
            String date = event.getDateEvent();
    
            newRegistration.setUser(user);
            newRegistration.setEvent(event);
            newRegistration.setDateR(date);;

            return iRegistrationRepository.save(newRegistration);
        }
    }
   

}
