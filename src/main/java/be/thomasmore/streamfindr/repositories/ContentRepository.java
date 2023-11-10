package be.thomasmore.streamfindr.repositories;

import be.thomasmore.streamfindr.model.Content;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContentRepository extends CrudRepository<Content, Integer> {

    @Query("SELECT c FROM Content c where TYPE(c) = Movie")
    Iterable<Content> findAllMovies();

    @Query("SELECT c FROM Content c where TYPE(c) = Show")
    Iterable<Content> findAllShows();

    @Query("SELECT c FROM Content c WHERE TYPE(c) = :contentType")
    Iterable<Content> findAllByContentType(@Param("contentType") Class<? extends Content> contentType);

    @Query("SELECT c FROM Content c ORDER BY (" +
            "SELECT AVG(r.score) FROM c.reviews r) DESC LIMIT 3")
    List<Content> findTop3ReviewedContent();

    //Ofwel alles in Native Query schrijven als het mogelijk is, ofwel String parameter converteren naar Type
    // (laatste is misschien het makkelijskte)
//    @Query(value = "SELECT * FROM CONTENT WHERE CONTENT_TYPE = :contentType OR :contentType IS NULL", nativeQuery = true)
//    List<Content> findByCombinedFilter(@Param("contentType") String contentType);

    @Query("SELECT c FROM Content c " +
            "LEFT JOIN c.cast actors " +
            "WHERE (:contentType IS NULL OR TYPE(c) = :contentType)" +
            " AND (:genre IS NULL OR :genre = '' OR c.genre = :genre)" +
            " AND (:keyword IS NULL OR :keyword = '' OR LOWER(c.name) LIKE LOWER(CONCAT('%',:keyword,'%'))" +
            " OR LOWER(actors.firstName) LIKE LOWER(CONCAT('%', :keyword, '%'))" +
            " OR LOWER(actors.lastName) LIKE LOWER(CONCAT('%', :keyword, '%'))" +
            " OR LOWER(c.director) LIKE LOWER(CONCAT('%', :keyword, '%')))" +
            " AND (:minScore IS NULL or :minScore <= (SELECT AVG(r.score) FROM c.reviews r))")
    List<Content> findByCombinedFilter(@Param("contentType") Class<? extends Content> contentType,
                                       @Param("genre") String genre,
                                       @Param("keyword") String keyword,
                                       @Param("minScore") Integer minScore);

//    @Query("SELECT c FROM Content c WHERE TYPE(c) = :contentType OR :contentType IS NULL")
//    List<Content> findByCombinedFilter(@Param("contentType") Class<? extends Content> contentType);

    @Query("SELECT DISTINCT c.genre FROM Content c")
    List<String> findDistinctGenres();
}


