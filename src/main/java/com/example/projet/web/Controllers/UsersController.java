package com.example.projet.web.Controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.projet.web.Models.Authentification;
import com.example.projet.web.Models.User;
import com.example.projet.web.Repositories.IUserRepository;
import com.example.projet.web.Services.AuthenticationService;
import com.example.projet.web.Services.IUserService;

import lombok.RequiredArgsConstructor;

@Controller
// @AllArgsConstructor
@RequiredArgsConstructor
public class UsersController {
    private final IUserService iUserService;
    private final IUserRepository iUserRepository;
    private final AuthenticationService authenticationService;
    private final  PasswordEncoder passwordEncoder;
    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("user", new User());
        return "signup";}

      @PostMapping("/signup")
    public String registerUser(User user) {
        user.setRole("user");
        iUserService.addUser(user);
        return "redirect:/signup?success"; 
    }
    @PostMapping("/updateUser")
    public String updateUser(@ModelAttribute User user, Authentification authentication) {
       
        String email = authentication.getEmail(); 
        User existingUser = authenticationService.findByEmail(email);
    
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
           
            String hashedPassword = passwordEncoder.encode(user.getPassword());
            existingUser.setPassword(hashedPassword);
        } else {
          
            existingUser.setPassword(existingUser.getPassword());
        }
    
 
        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setNumtel(user.getNumtel());
    
        
        iUserService.updateUser(existingUser);
    
        return "redirect:/Profile?success"; 
    }
    
    


    @GetMapping("/getAllUser")
    public String getAllUser(Model model) {
        List<User> users = iUserService.getAllUser();
        model.addAttribute("users", users);
         return "Signup"; }

    @GetMapping("/getUserById/{idUser}")
    public User getUserById(@PathVariable Long idUser) {
        return iUserService.getUserById(idUser);
    }

    @DeleteMapping("/deleteUserById/{idUser}")
    public void deleteUserById(@PathVariable Long idUser) {
        iUserService.deleteUserById(idUser);
    }

    @PutMapping("/password")
    public ResponseEntity<String> updatePassword(
            @PathVariable Long id,
            @RequestBody User user
    ) {
        iUserService.updatePassword(id, user.getNewPassword());
        return ResponseEntity.ok("Password updated successfully.");
    }
   @GetMapping("/profile/{id}/password")
    public String showPasswordUpdateForm(@PathVariable Long id, Model model) {
       
        User user = iUserRepository.findById(id).orElse(null);
        model.addAttribute("user", user);
        return "updatePassword"; 
    }

    @PostMapping("/profile/{id}/password")
    public String updatePassword(@PathVariable Long id, @RequestParam String newPassword, Model model) {
        if (newPassword == null || newPassword.isEmpty()) {
            model.addAttribute("error", "Password cannot be empty.");
            return "updatePassword"; 
        }
        iUserService.updatePassword(id, newPassword);
    return "redirect:/Profile?passwordSuccess=true";
    }
   

   
}
