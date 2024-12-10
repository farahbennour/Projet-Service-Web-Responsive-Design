package com.example.projet.web.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.projet.web.Models.Event;

public interface IEventRepository extends JpaRepository<Event,Long>  {
   
    
    Event findByTitre(String titre);
    
    @Query("SELECT e FROM Event e JOIN FETCH e.category WHERE e.id = :id")
    Optional<Event> findByIdWithCategory(@Param("id") Long id); 

    @Query("SELECT e FROM Event e WHERE e.lieuEvent = :lieuEvent")
    List<Event> findEventsByLocation(@Param("lieuEvent") String lieuEvent);
}
