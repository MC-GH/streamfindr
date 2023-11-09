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
}


