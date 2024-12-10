package com.example.projet.web.Controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.projet.web.Models.Event;
import com.example.projet.web.Models.Registration;
import com.example.projet.web.Models.User;
import com.example.projet.web.Repositories.IEventRepository;
import com.example.projet.web.Repositories.IRegistrationRepository;
import com.example.projet.web.Repositories.IUserRepository;
import com.example.projet.web.Services.AuthenticationService;
import com.example.projet.web.Services.IRegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@AllArgsConstructor
public class RegistrationController {
    private IRegistrationService iRegistrationService;
    private IRegistrationRepository registrationRepository;
    private IUserRepository userRepository;
    private IEventRepository eventRepository;
    private AuthenticationService authenticationService;
    @PostMapping("/register")
    public String registerForEvent(Long eventId, Model model) {
        
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByEmail(currentUserEmail);

      
        Event event = eventRepository.findById(eventId).orElseThrow(() ->
            new RuntimeException("Event not found"));

        if (registrationRepository.findRegistrationByUserIdAndEventId(currentUser.getId(), event.getId()) != null) {
            model.addAttribute("message", "You are already registered for this event.");
        } else {
           
            Registration registration = new Registration();
            registration.setUser(currentUser);
            registration.setEvent(event);
            registration.setDateR(LocalDate.now().toString());

            registrationRepository.save(registration);
            model.addAttribute("message", "User registered successfully.");
        }
        return "redirect:/home"; 
    }
    @PostMapping("/registerUserForEvent/{userId}/{eventId}")
    public Registration registerUserForEvent(@PathVariable Long userId ,@PathVariable Long eventId) {
        return iRegistrationService.registerUserForEvent(userId, eventId);
    }
 
   
@PostMapping("/unregisterUserFromEvent/{userId}/{eventId}")
public String unregisterUserFromEvent(@PathVariable Long userId, @PathVariable Long eventId, RedirectAttributes redirectAttributes) {
    boolean unregistered = iRegistrationService.unregisterUserFromEvent(userId, eventId);
    if (unregistered) {
        redirectAttributes.addFlashAttribute("message", "Unregistered successfully.");
    } else {
        redirectAttributes.addFlashAttribute("message", "Registration not found.");
    }

    return "redirect:/my-registrations";
}
    @GetMapping("getRegistrationsByUser/{userId}")
    public List<Registration> getRegistrationsByUser(@PathVariable Long userId) {
        return iRegistrationService.getRegistrationsByUser(userId);
    }
    
    @GetMapping("getRegistrationsByEvent/{eventId}")
    public List<Registration> getRegistrationsByEvent(@PathVariable Long eventId) {
        return iRegistrationService.getRegistrationsByEvent(eventId);
    }
    @GetMapping("/my-registrations")
public String getUserRegistrations(Model model) {
   
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String email = authentication.getName();
    User user = authenticationService.findByEmail(email); 
   
    List<Registration> registrations = iRegistrationService.getRegistrationsByUser(user.getId());
    
    model.addAttribute("registrations", registrations);
    model.addAttribute("username", user.getName());
    return "user-registrations"; 
}

}
