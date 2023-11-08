package be.thomasmore.streamfindr.model;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
    public Account() {
    }
}
