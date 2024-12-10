package com.example.projet.web.Models;

import com.example.projet.web.Repositories.IUserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret-key}")
    private String secretKeyBase64;

    @Autowired
    private IUserRepository userRepository;

    public JwtUtil() {
   
    }

    private byte[] getSecretKey() {
        return Base64.getDecoder().decode(secretKeyBase64);
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .claim("roles", userRepository.findByEmail(username).getRole())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) 
                .signWith(SignatureAlgorithm.HS256, getSecretKey()) 
                .compact();
    }

    public String extractEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject(); 
    }

    public boolean validateToken(String token, String username) {
        final String email = extractEmail(token);
        return (email.equals(username) && !isTokenExpired(token));  
    }

    private boolean isTokenExpired(String token) {
        Date expirationDate = Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expirationDate.before(new Date());
    }
}
