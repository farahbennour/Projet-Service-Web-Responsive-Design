    package com.example.projet.web.Models;

    import java.io.Serializable;
    import com.fasterxml.jackson.annotation.JsonIgnore;

    import jakarta.persistence.Entity;
    import jakarta.persistence.GeneratedValue;
    import jakarta.persistence.GenerationType;
    import jakarta.persistence.Id;
    import jakarta.persistence.JoinColumn;
    import jakarta.persistence.ManyToOne;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;


    @NoArgsConstructor
    @Getter
    @Setter
    @Entity
    public class Registration implements Serializable{
        @Id
        @GeneratedValue(strategy=GenerationType.IDENTITY)
        private Long idR;

        @ManyToOne
        @JsonIgnore
        @JoinColumn(name ="user_id")
        private User user;
        
        @ManyToOne
        @JsonIgnore
        @JoinColumn(name = "event_id")
        private Event event;

        private String dateR;
        @Override
public String toString() {
    return "Registration{" +
           "idR=" + idR +
           ", dateR='" + dateR + '\'' +
           "}"; 
}
    }
