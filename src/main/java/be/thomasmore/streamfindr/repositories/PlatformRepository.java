package be.thomasmore.streamfindr.repositories;
import be.thomasmore.streamfindr.model.Platform;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface PlatformRepository extends CrudRepository<Platform,Integer> {

    List<Platform> findAll();
    Optional<Platform> findFirstByOrderByMonthlyPriceInUsdAsc();
    Optional<Platform> findFirstByOrderByMonthlyPriceInUsdDesc();
    Optional<Platform> findFirstByIdLessThanOrderByIdDesc(Integer id);
    Optional<Platform> findFirstByOrderByIdDesc();
    Optional<Platform> findFirstByIdGreaterThanOrderByIdAsc(Integer id);
    Optional<Platform> findFirstByOrderByIdAsc();
    @Query("SELECT p FROM Platform p" +
            " WHERE (:keyword IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%',:keyword,'%')))" +
            " AND (:maxMonthlyFee IS NULL OR p.monthlyPriceInUsd <= :maxMonthlyFee)" +
            " AND (:annualSubscriptionPossible IS NULL OR p.yearlySubscriptionPossible = :annualSubscriptionPossible)")
    List<Platform> findByCombinedFilter(@Param("keyword") String keyword,
                                        @Param("maxMonthlyFee") Double maxMonthlyFee,
                                        @Param("annualSubscriptionPossible") Boolean annualSubscriptionPossible);

}

