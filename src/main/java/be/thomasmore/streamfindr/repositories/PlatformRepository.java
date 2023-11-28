package be.thomasmore.streamfindr.repositories;

import be.thomasmore.streamfindr.model.Platform;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PlatformRepository extends CrudRepository<Platform,Integer> {

    List<Platform> findAll();

    Optional<Platform> findFirstByOrderByMonthlyPriceAsc();
    Optional<Platform> findFirstByOrderByMonthlyPriceDesc();

    @Query("SELECT p FROM Platform p" +
            " WHERE (:keyword IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%',:keyword,'%')))" +
            " AND (:annualSubscriptionPossible IS NULL OR p.yearlySubscriptionPossible = :annualSubscriptionPossible)")
    List<Platform> findByCombinedFilter(@Param("keyword") String keyword,
                                        @Param("annualSubscriptionPossible") Boolean annualSubscriptionPossible);

}
