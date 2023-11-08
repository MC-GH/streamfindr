package be.thomasmore.streamfindr.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Review {
     @Id
    private Integer id;
     private int score;
     private String reviewText;
     @ManyToOne
     private Account account;
     @ManyToOne
     private Content content;

    public Review() {
    }
}
