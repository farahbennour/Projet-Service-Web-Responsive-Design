package com.example.projet.web.Services;

import com.example.projet.web.Models.User;
import com.example.projet.web.Repositories.IUserRepository; // Si vous avez un repository pour récupérer l'utilisateur
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final IUserRepository userRepository; 
public CustomUserDetailsService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

   @Override
public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByEmail(username);

   
    return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
            AuthorityUtils.createAuthorityList("ROLE_" + user.getRole()));  
}


}
