package be.thomasmore.streamfindr.repositories;
import be.thomasmore.streamfindr.model.Actor;
import org.springframework.data.repository.CrudRepository;

public interface ActorRepository extends CrudRepository<Actor,Integer> {
}
