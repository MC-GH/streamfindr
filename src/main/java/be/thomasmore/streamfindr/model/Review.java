package be.thomasmore.streamfindr.model;

import jakarta.persistence.*;

@Entity
public class Review {
     @Id
     @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "review_generator")
     @SequenceGenerator(name = "review_generator", sequenceName = "review_seq", allocationSize = 1)
    private Integer id;
     private int score;
     private String reviewText;
     @ManyToOne (fetch = FetchType.LAZY)
     private Account account;
     @ManyToOne (fetch = FetchType.LAZY)
     private Content content;

    public Review() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }
}
