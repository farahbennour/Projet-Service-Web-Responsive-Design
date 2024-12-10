package com.example.projet.web.Controllers;

import com.example.projet.web.Models.Category;
import com.example.projet.web.Models.Event;
import com.example.projet.web.Models.Registration;
import com.example.projet.web.Models.User;
import com.example.projet.web.Repositories.ICategoryRepository;
import com.example.projet.web.Repositories.IEventRepository;
import com.example.projet.web.Repositories.IRegistrationRepository;
import com.example.projet.web.Services.AuthenticationService;
import com.example.projet.web.Services.EventService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
@Autowired
private AuthenticationService authenticationService;

   @Autowired
   private EventService eventService;
   @Autowired
   private IEventRepository eventRepository;
   @Autowired
   private ICategoryRepository categoryRepository;
   @Autowired
   private IRegistrationRepository registrationRepository;
  @GetMapping("/home")
public String home(
        @RequestParam(value = "lieuEvent", required = false) String lieuEvent,
        @RequestParam(value = "categoryId", required = false) Long categoryId,
        @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
        @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
        @RequestParam(value = "registerEventId", required = false) Long registerEventId, // Added
        Model model
) {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String email = authentication.getName();
    User user = authenticationService.findByEmail(email);
    String username = user.getName();

   
    if (registerEventId != null) {
        Event event = eventRepository.findById(registerEventId).orElse(null);
        if (event != null) {
            // verifier si l'utilisateur est participé à cet événement ou pas 
            if (registrationRepository.findRegistrationByUserIdAndEventId(user.getId(), event.getId()) == null ) {
                Registration registration = new Registration();
                registration.setUser(user);
                registration.setEvent(event);
                registration.setDateR(LocalDate.now().toString());
                registrationRepository.save(registration);
                model.addAttribute("message", "User registered successfully.");
            } else {
                model.addAttribute("message", "You are already registered for this event.");
            }
        } else {
            model.addAttribute("message", "Event not found.");
        }
    }

 
    List<String> locations = eventService.getAllEvents()
            .stream()
            .map(Event::getLieuEvent)
            .distinct()
            .sorted()
            .toList();

  
    List<Category> categories = categoryRepository.findAll();


    List<Event> events = eventService.getAllEvents();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    if (lieuEvent != null && !lieuEvent.isEmpty()) {
        events = events.stream()
                .filter(event -> lieuEvent.equals(event.getLieuEvent()))
                .toList();
    }
    if (categoryId != null) {
        events = events.stream()
                .filter(event -> event.getCategory().getId().equals(categoryId))
                .toList();
    }
    if (startDate != null && endDate != null) {
        events = events.stream()
                .filter(event -> {
                    try {
                        Date eventDate = sdf.parse(event.getDateEvent());
                        return !eventDate.before(startDate) && !eventDate.after(endDate);
                    } catch (ParseException e) {
                        return false; 
                    }
                })
                .toList();
    }

    model.addAttribute("username", username);
    model.addAttribute("event", events);
    model.addAttribute("locations", locations);
    model.addAttribute("categories", categories);
    model.addAttribute("currentLocation", lieuEvent);
    model.addAttribute("currentCategory", categoryId);
    model.addAttribute("startDate", startDate);
    model.addAttribute("endDate", endDate);
    return "home";
}


}