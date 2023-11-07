package be.thomasmore.streamfindr.repositories;

import be.thomasmore.streamfindr.model.Movie;
import org.springframework.data.repository.CrudRepository;

public interface MovieRepository extends CrudRepository<Movie,Integer> {
}
