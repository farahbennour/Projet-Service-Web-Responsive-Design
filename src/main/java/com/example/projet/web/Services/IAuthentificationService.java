package com.example.projet.web.Services;

import com.example.projet.web.Models.User;

public interface IAuthentificationService {
    public User signup (User user);
    public boolean login(String email , String password);
}
