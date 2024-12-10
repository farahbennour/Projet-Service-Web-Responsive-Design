package com.example.projet.web.Services;

import java.util.List;

import com.example.projet.web.Models.Registration;

public interface IRegistrationService {
    public Registration registerUserForEvent(Long userId, Long eventId);
    public boolean unregisterUserFromEvent(Long userId,Long eventId);
    public List<Registration> getRegistrationsByUser(Long userId);
    public List<Registration> getRegistrationsByEvent(Long eventId);
}
