package be.thomasmore.streamfindr.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "contentType",
        discriminatorType = DiscriminatorType.STRING)
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank
    private String name;
    @NotBlank
    private String director;
    @NotBlank
    private String genre;
    @Column(length = 500)
    private String plotDescription;
    private String imageUrl;
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Actor> actors;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "content", cascade = CascadeType.ALL)
    private Set<Review> reviews;
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Platform> platforms;
    @Column(name = "contentType", insertable = false, updatable = false)
    private String contentType;


    public Content() {
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

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPlotDescription() {
        return plotDescription;
    }

    public void setPlotDescription(String plotDescription) {
        this.plotDescription = plotDescription;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String posterSrc) {
        this.imageUrl = posterSrc;
    }

    public Set<Actor> getActors() {
        return actors;
    }

    public void setActors(Set<Actor> cast) {
        this.actors = cast;
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    public Set<Platform> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(Set<Platform> availableOnPlatforms) {
        this.platforms = availableOnPlatforms;
    }

    public String getContentType() {
                return this.contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }



}
