package com.example.projet.web.Services;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.projet.web.Models.User;
import com.example.projet.web.Repositories.IUserRepository;

import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor

public class UserService implements IUserService {
private IUserRepository iUserRepository;
private PasswordEncoder passwordEncoder;



@Override
public User findByEmail(String email) {
    return iUserRepository.findByEmail(email);
}

@Override
public User addUser(User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    return iUserRepository.save(user);
}

@Override
public User updateUser(User user) {
    // user.setPassword(passwordEncoder.encode(user.getPassword()));
    return iUserRepository.save(user);
}

@Transactional
public void updatePassword(Long id, String newPassword) {
    String hashedPassword = passwordEncoder.encode(newPassword);
    iUserRepository.updatePassword(id, hashedPassword);
}


@Override
public User getUserById(Long idUser) {
   return iUserRepository.findById(idUser).orElse(null);
}
public boolean checkCredentials(String email, String password) {
    User user = iUserRepository.findByEmail(email);
    if (user == null) {
        return false; 
    }
    return passwordEncoder.matches(password, user.getPassword());
}

@Override
public void deleteUserById(Long idUser) {
    iUserRepository.deleteById(idUser);
}



@Override
public List<User> getAllUser() {
    return iUserRepository.findAll();
}



}

