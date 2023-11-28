package be.thomasmore.streamfindr.model;
import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_generator")
    @SequenceGenerator(name = "account_generator", sequenceName = "account_seq", allocationSize = 1)
    private Integer id;
    private String userName;
    private String password;
    private String email;
    private String firstName;
    private String lastName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "account")
    private Set<Review> reviews;

    @ManyToMany
    @JoinTable(
            name = "account_platform",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "platform_id"))
    private Set<Platform> memberOfPlatforms;

    public Account() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Set<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    public Set<Platform> getMemberOfPlatforms() {
        return memberOfPlatforms;
    }

    public void setMemberOfPlatforms(Set<Platform> memberOfPlatforms) {
        this.memberOfPlatforms = memberOfPlatforms;
    }
}
