package be.thomasmore.streamfindr.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.Set;

@Entity
public class Platform {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotBlank
    private String uniqueSellingPoint;
    @NotNull
    @DecimalMin(value = "0.00")
    private Double monthlyPriceInUsd;
    private boolean yearlySubscriptionPossible;
    @Column(nullable = true)
    private Double yearlyPriceInUsd;
    private String imageUrl;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "platforms")
    private Set<Content> content;

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

    public String getImageUrl() {
        return this.imageUrl;
    }

    public Set<Content> getContent() {
        return content;
    }

    public void setContent(Set<Content> catalogue) {
        this.content = catalogue;
    }

    public void setImageUrl(String logoSrc) {
        this.imageUrl = logoSrc;
    }
}
