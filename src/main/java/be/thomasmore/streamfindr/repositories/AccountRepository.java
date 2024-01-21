package be.thomasmore.streamfindr.repositories;
import be.thomasmore.streamfindr.model.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account, Integer> {

    Optional<Account> findByUsername(String name);

}
