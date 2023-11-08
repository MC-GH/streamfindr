package be.thomasmore.streamfindr.repositories;

import be.thomasmore.streamfindr.model.Content;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ContentRepository extends CrudRepository<Content, Integer> {

    @Query("SELECT c FROM Content c where TYPE(c) = Movie")
    Iterable<Content> findAllMovies();

    @Query("SELECT c FROM Content c where TYPE(c) = Show")
    Iterable<Content> findAllShows();

//    @Query(value = "SELECT c.* FROM Content c WHERE c.", nativeQuery = true)
//    Iterable<Content> findMostRecent9Results(int currentYear);


    //Using Native Query we can filter on content_type
    @Query(value = "SELECT c.* FROM Content c WHERE c.content_type = :contentType", nativeQuery = true)
    Iterable<Content> findAllByContentType(@Param("contentType") String contentType);
}


