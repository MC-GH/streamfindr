package be.thomasmore.streamfindr.model;
import jakarta.persistence.*;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "contentType",
        discriminatorType = DiscriminatorType.STRING)
public class Content {
    @Id
    private Integer id;
    private String name;
    private String director;
    private String genre;
    @Column(length = 500)
    private String plotDescription;
    private String posterSrc;
    @ManyToMany
    @JoinTable(
                name="content_actor",
            joinColumns = @JoinColumn(name = "content_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id"))
    private Set<Actor> cast;
    @OneToMany(mappedBy = "content")
    private Set<Review> reviews;
    @ManyToMany
    @JoinTable(
            name = "content_platform",
            joinColumns = @JoinColumn(name = "content_id"),
            inverseJoinColumns = @JoinColumn(name = "platform_id"))
    private Set<Platform> availableOnPlatforms;

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

    public String getPosterSrc() {
        return posterSrc;
    }

    public void setPosterSrc(String posterSrc) {
        this.posterSrc = posterSrc;
    }

    public Set<Actor> getCast() {
        return cast;
    }

    public void setCast(Set<Actor> cast) {
        this.cast = cast;
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    public Set<Platform> getAvailableOnPlatforms() {
        return availableOnPlatforms;
    }

    public void setAvailableOnPlatforms(Set<Platform> availableOnPlatforms) {
        this.availableOnPlatforms = availableOnPlatforms;
    }
}
