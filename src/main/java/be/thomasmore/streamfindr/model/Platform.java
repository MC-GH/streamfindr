package be.thomasmore.streamfindr.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Platform {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    private String uniqueSellingPoint;
    private Double monthlyPriceInUsd;
    private boolean yearlySubscriptionPossible;
    @Column(nullable = true)
    private Double yearlyPriceInUsd;
    private String logoSrc;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "platforms")
    private Set<Content> content;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "platforms")
    private Set<Account> accounts;
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

    public Set<Content> getContent() {
        return content;
    }

    public void setContent(Set<Content> catalogue) {
        this.content = catalogue;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> members) {
        this.accounts = members;
    }
}
