package com.example.projet.web.Models;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Notification implements Serializable{
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Event event;


    public void setNomEvent(String nomEvent) { 
        this.nomEvent = nomEvent;
    }
    
    public void setDateEvent(String dateEvent ) { 
        this.dateEvent =  dateEvent;
    }
    public void setLieuEvent(String lieuEvent) { 
        this.lieuEvent = lieuEvent;
    }
     private String nomEvent;
     @Temporal(TemporalType.DATE)
     private String lieuEvent;
     private String dateEvent;
}
