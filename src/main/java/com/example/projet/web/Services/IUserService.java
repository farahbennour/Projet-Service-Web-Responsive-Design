package com.example.projet.web.Services;

import java.util.List;

import com.example.projet.web.Models.User;

public interface IUserService {
    public User addUser(User user);
    public User updateUser(User user);
    // public User updatePassword(User user);
    public User getUserById(Long idUser);
    public void deleteUserById(Long idUser);
    public boolean checkCredentials(String email, String password);
    public List<User> getAllUser();
    public void updatePassword(Long id, String newPassword);
    public User findByEmail(String username);
    
    
   
}
