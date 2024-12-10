package com.example.projet.web.Models;



import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@AllArgsConstructor
@Getter
@Setter
@Entity
@ToString
@Data
public class Authentification {
@Id
private String email;
@Id 
private String password;
}
