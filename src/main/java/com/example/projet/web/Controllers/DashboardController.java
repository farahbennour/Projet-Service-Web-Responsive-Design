package com.example.projet.web.Controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.projet.web.Models.User;
import com.example.projet.web.Repositories.IUserRepository;
import com.example.projet.web.Services.AuthenticationService;

@Controller
public class DashboardController {
@Autowired
private AuthenticationService authenticationService;
@Autowired
private IUserRepository userRepository;
    
@GetMapping("/dashboard")
public String getDashboard(Model model) {
    // parcourir tous les utilisateurs du DB
    List<User> users = userRepository.findAll();
   List<User> filteredUsers = users.stream()
    .filter(e -> e.getRole().equals("user"))
    .collect(Collectors.toList());
    model.addAttribute("users", filteredUsers);
    
    //pour extraire l'user authentifier
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String email = authentication.getName();
    User user = authenticationService.findByEmail(email);
    String username = user.getName();
    model.addAttribute("username", username);
    
    return "dashboard";
}
 @PostMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        // trouver l'user 
        User user = userRepository.findById(id).orElse(null);
        
        if (user != null) {
            // supprimer l'user
            userRepository.delete(user);
        }
        
       
        return "redirect:/dashboard";
    }
}

    


