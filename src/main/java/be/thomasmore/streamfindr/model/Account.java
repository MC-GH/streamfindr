package be.thomasmore.streamfindr.model;
import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Account {
    @Id
    private Integer id;
    private String userName;
    private String password;
    private String email;
    private String firstName;
    private String lastName;

    @OneToMany(mappedBy = "account")
    private Set<Review> reviews;

    @ManyToMany
    @JoinTable(
            name = "account_platform",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "platform_id"))
    private Set<Platform> memberOfPlatforms;

    public Account() {
    }
}
