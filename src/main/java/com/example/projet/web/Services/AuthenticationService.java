package com.example.projet.web.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.projet.web.Models.User;
import com.example.projet.web.Repositories.IUserRepository;

@Service
public class AuthenticationService implements IAuthentificationService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public User signup(User user) {
       
            user.setPassword(passwordEncoder.encode(user.getPassword())); // Hashing the password
            return userRepository.save(user);
    }
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean login(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            System.out.println("User not found for email: " + email);
            return false; 
        }
        boolean matches = passwordEncoder.matches(password, user.getPassword());
        if (!matches) {
            System.out.println("Password does not match for email: " + email);
        } else {
            System.out.println("User logged in successfully: " + email);
        }
        return matches;
    }


}

