package be.thomasmore.streamfindr.model;
import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String firstName;
    private String lastName;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "actors")
    private Set<Content> content;

    public Actor() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<Content> getContent() {
        return content;
    }

    public void setContent(Set<Content> repertoire) {
        this.content = repertoire;
    }
}

