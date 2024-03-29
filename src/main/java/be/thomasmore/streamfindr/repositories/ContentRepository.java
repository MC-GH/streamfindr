package be.thomasmore.streamfindr.repositories;

import be.thomasmore.streamfindr.model.Content;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ContentRepository extends CrudRepository<Content, Integer> {

    @Query("SELECT c FROM Content c ORDER BY (" +
            "SELECT AVG(r.score) FROM c.reviews r) DESC LIMIT 3")
    List<Content> findTop3ReviewedContent();

    @Query("SELECT c FROM Content c " +
            "LEFT JOIN c.actors actors " +
            "LEFT JOIN c.platforms platforms " +
            "WHERE (:contentType IS NULL OR :contentType='' OR c.contentType = :contentType)" +
            " AND (:genre IS NULL OR :genre='' OR c.genre = :genre)" +
            " AND (:platformName IS NULL OR :platformName='' OR platforms.name = :platformName)" +
            " AND (:keyword IS NULL OR :keyword = '' OR LOWER(c.name) LIKE LOWER(CONCAT('%',:keyword,'%'))" +
            " OR LOWER(actors.name) LIKE LOWER(CONCAT('%', :keyword, '%'))" +
            " OR LOWER(c.director) LIKE LOWER(CONCAT('%', :keyword, '%')))" +
            " AND (:minScore IS NULL OR :minScore=0 OR :minScore <= (SELECT AVG(r.score) FROM c.reviews r))")
    List<Content> findByCombinedFilter(@Param("contentType") String contentType,
                                       @Param("genre") String genre,
                                       @Param("platformName") String platformName,
                                       @Param("keyword") String keyword,
                                       @Param("minScore") Integer minScore);

    @Query("SELECT DISTINCT c.genre FROM Content c")
    List<String> findDistinctGenres();

    @Query("SELECT DISTINCT p.name FROM Content c JOIN c.platforms p")
    List<String> findDistinctPlatformNames();

    @Query("SELECT DISTINCT c.contentType FROM Content c")
    List<String> findDistinctContentTypes();

    Optional<Content> findFirstByIdLessThanOrderByIdDesc(Integer id);
    Optional<Content> findFirstByOrderByIdDesc();
    Optional<Content> findFirstByIdGreaterThanOrderByIdAsc(Integer id);

    Optional<Content> findFirstByOrderByIdAsc();

    @Query("SELECT AVG(r.score) from Review r WHERE r.content = :content")
    Double calculateAverageRatingForContent(@Param("content") Content content);

    List<Content> findAll();

}


