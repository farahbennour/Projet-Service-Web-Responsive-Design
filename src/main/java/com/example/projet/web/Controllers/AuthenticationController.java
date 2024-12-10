package com.example.projet.web.Controllers;

import com.example.projet.web.Models.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.projet.web.Models.User;
import com.example.projet.web.Services.AuthenticationService;

@Controller
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User()); 
        return "login"; 
    }


    @PostMapping("/login")
    public String login(@ModelAttribute User user, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        boolean isAuthenticated = authenticationService.login(user.getEmail(), user.getPassword());

        if (isAuthenticated) {
            User authenticatedUser = authenticationService.findByEmail(user.getEmail());
            String token = jwtUtil.generateToken(authenticatedUser.getEmail());

            Cookie jwtCookie = new Cookie("jwtToken", token);
            jwtCookie.setHttpOnly(true);
            jwtCookie.setPath("/");
            response.addCookie(jwtCookie);

            if ("admin".equalsIgnoreCase(authenticatedUser.getRole())) {
                return "redirect:/dashboard";
            } else if ("user".equalsIgnoreCase(authenticatedUser.getRole())) {
                return "redirect:/home";
            } else {
                redirectAttributes.addFlashAttribute("error", "Invalid role");
                return "redirect:/auth/login?danger=true";
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "Invalid email or password");
            return "redirect:/auth/login?danger=true";
        }
    }


    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
       
        request.getSession().invalidate();

        // supprimer le jwt cookie
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwtToken".equals(cookie.getName())) {
                    cookie.setValue(null);
                    cookie.setPath("/"); 
                    cookie.setHttpOnly(true); 
                    cookie.setMaxAge(0); 
                    response.addCookie(cookie);
                    break; 
                }
            }
        }

        return "redirect:/auth/login"; 
    }

}



