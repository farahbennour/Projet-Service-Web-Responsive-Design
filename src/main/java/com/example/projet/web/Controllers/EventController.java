package com.example.projet.web.Controllers;
import com.example.projet.web.Models.Category;
import com.example.projet.web.Models.Event;
import com.example.projet.web.Models.Notification;
import com.example.projet.web.Repositories.IEventRepository;
import com.example.projet.web.Repositories.NotificationRepository;
import com.example.projet.web.Services.ICategoryService;
import com.example.projet.web.Services.IEventService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;


@AllArgsConstructor
@Controller
public class EventController {
    private NotificationRepository notificationRepository;
    private IEventService iEventService;
    private ICategoryService iCategoryService;
    private IEventRepository iEventRepository;
    @GetMapping("/event/getEventByTitle")
    @ResponseBody
    public Event getEventByTitle(@RequestParam("id") Long id) {
        return iEventService.getEventById(id);
    }

    @PostMapping("/addEvent")
    public String addEvent(@ModelAttribute Event event, Model model) {
        iEventService.addEvent(event);
        Event savedEvent = iEventRepository.save(event);
        
      
      Notification notification = new Notification();
      notification.setNomEvent(event.getTitre());
      notification.setLieuEvent(event.getLieuEvent());
      notification.setDateEvent(event.getDateEvent() );
      notification.setEvent(savedEvent);
      notificationRepository.save(notification);
        model.addAttribute("event", new Event()); 
        model.addAttribute("events", iEventService.getAllEvents());
        return "redirect:/eventForm?success";
    }

    @GetMapping("/allEvent")
    public String getAllEvents(Model model) {
        model.addAttribute("events", iEventService.getAllEvents());
        return "event-list";
    }

    @GetMapping("/event/getEventById")
    @ResponseBody
    public Event getEventById(@RequestParam("id") Long id) {
        Event event = iEventService.getEventById(id);
        if (event == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found");
        }
        return event;
    }

    @GetMapping("/eventForm")
    public String showEventForm(Model model) {
        List<Event> events = iEventService.getAllEvents();
        List<Category> categories = iCategoryService.getAllCategory();
        model.addAttribute("event", new Event());
        model.addAttribute("events", events);
        model.addAttribute("categories", categories);
        return "Event_Form";
    }

    @PostMapping("/updateEvent")
    public String updateEvent( @ModelAttribute Event event, Model model) {
        Event existingEvent = iEventService.getEventById(event.getId());
  
        if (existingEvent != null) {
            existingEvent.setTitre(event.getTitre());
            existingEvent.setDescription(event.getDescription());
            existingEvent.setDateEvent(event.getDateEvent());
            existingEvent.setLieuEvent(event.getLieuEvent());
            existingEvent.setNbPlace(event.getNbPlace());
            existingEvent.setCout(event.getCout());
            existingEvent.setCategory(event.getCategory());

            iEventService.updateEvent(existingEvent);
            return "redirect:/eventForm?updated";
        }
        return "redirect:/eventForm?error";
    }
    

    @PostMapping("/deleteEvent")
    public String deleteEvent(@ModelAttribute Event event, Model model) {
        iEventService.deleteEventById(event.getId());
        return "redirect:/eventForm?deleted";
    }
  
}


