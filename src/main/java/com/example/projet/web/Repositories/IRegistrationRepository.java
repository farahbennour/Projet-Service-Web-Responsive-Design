package com.example.projet.web.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.projet.web.Models.Registration;


public interface IRegistrationRepository extends JpaRepository<Registration,Long>{
    @Query("SELECT r FROM Registration r WHERE r.user.id = :userId AND r.event.id = :eventId")
    Registration findRegistrationByUserIdAndEventId(
        @Param("userId") Long userId, 
        @Param("eventId") Long eventId
    );

    @Query("SELECT r FROM Registration r WHERE r.user.id = :userId")
    List<Registration> findByUserId(@Param("userId") Long userId);

    @Query("SELECT r FROM Registration r WHERE r.event.id = :eventId")
    List<Registration> findByEventId(@Param("eventId") Long eventId);
}
