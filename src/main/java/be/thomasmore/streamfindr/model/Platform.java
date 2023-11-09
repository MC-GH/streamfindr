package be.thomasmore.streamfindr.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import java.util.Set;

@Entity
public class Platform {
    @Id
    private Integer id;
    private String name;
    private String description;
    private String uniqueSellingPoint;
    private double monthlyPrice;
    private boolean yearlySubscriptionPossible;
    private double yearlyPrice;
    @ManyToMany(mappedBy = "availableOnPlatforms")
    private Set<Content> catalogue;

    @ManyToMany(mappedBy = "memberOfPlatforms")
    private Set<Account> members;
    public Platform() {
    }


}
