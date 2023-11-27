package be.thomasmore.streamfindr.repositories;

import be.thomasmore.streamfindr.model.Platform;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PlatformRepository extends CrudRepository<Platform,Integer> {

    List<Platform> findAll();

    Optional<Platform> findFirstByOrderByMonthlyPriceAsc();
    Optional<Platform> findFirstByOrderByMonthlyPriceDesc();

}
