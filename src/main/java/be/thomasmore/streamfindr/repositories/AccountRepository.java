package be.thomasmore.streamfindr.repositories;

import be.thomasmore.streamfindr.model.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Integer> {
}
