package com.example.projet.web.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.projet.web.Models.User;
import com.example.projet.web.Services.AuthenticationService;

@Controller
public class ProfileController {
 @Autowired
private AuthenticationService authenticationService;
 @GetMapping("/Profile")
    public String Profile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication);
        String email = authentication.getName();
        User user = authenticationService.findByEmail(email);
        String username = user.getName();
        String phone= user.getNumtel();
        Long id = user.getId();
        model.addAttribute("username", username)
        .addAttribute("email", email)
        .addAttribute("phone", phone)
        .addAttribute("user", user)
        .addAttribute("id", id);
        return "Profile"; 
    }
}
