package be.thomasmore.streamfindr.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Platform {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "platform_generator")
    @SequenceGenerator(name = "platform_generator", sequenceName = "platform_seq", allocationSize = 1)
    private Integer id;
    private String name;
    private String description;
    private String uniqueSellingPoint;
    private Double monthlyPriceInUsd;
    private boolean yearlySubscriptionPossible;
    @Column(nullable = true)
    private Double yearlyPriceInUsd;
    private String logoSrc;
    @ManyToMany(mappedBy = "availableOnPlatforms")
    private Set<Content> catalogue;

    @ManyToMany(mappedBy = "memberOfPlatforms")
    private Set<Account> members;
    public Platform() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUniqueSellingPoint() {
        return uniqueSellingPoint;
    }

    public void setUniqueSellingPoint(String uniqueSellingPoint) {
        this.uniqueSellingPoint = uniqueSellingPoint;
    }

    public Double getMonthlyPriceInUsd() {
        return monthlyPriceInUsd;
    }

    public void setMonthlyPriceInUsd(Double monthlyPrice) {
        this.monthlyPriceInUsd = monthlyPrice;
    }

    public boolean isYearlySubscriptionPossible() {
        return yearlySubscriptionPossible;
    }

    public void setYearlySubscriptionPossible(boolean yearlySubscriptionPossible) {
        this.yearlySubscriptionPossible = yearlySubscriptionPossible;
    }

    public Double getYearlyPriceInUsd() {
        return yearlyPriceInUsd;
    }

    public void setYearlyPriceInUsd(Double yearlyPrice) {
        this.yearlyPriceInUsd = yearlyPrice;
    }

    public String getLogoSrc() {
        return this.logoSrc;
    }

    public Set<Content> getCatalogue() {
        return catalogue;
    }

    public void setCatalogue(Set<Content> catalogue) {
        this.catalogue = catalogue;
    }

    public Set<Account> getMembers() {
        return members;
    }

    public void setMembers(Set<Account> members) {
        this.members = members;
    }
}
