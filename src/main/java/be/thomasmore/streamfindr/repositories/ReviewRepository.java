package be.thomasmore.streamfindr.repositories;

import be.thomasmore.streamfindr.model.Review;
import org.springframework.data.repository.CrudRepository;

public interface ReviewRepository extends CrudRepository<Review, Integer> {
}
