package be.thomasmore.streamfindr.repositories;

import be.thomasmore.streamfindr.model.Show;
import org.springframework.data.repository.CrudRepository;

public interface ShowRepository extends CrudRepository<Show,Integer> {
}
