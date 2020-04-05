package cane.brothers.tags;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by cane
 */
@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    /**
     * find all Tags like projections
     *
     * @param type class of projection
     * @param <T>  type of the tag projection
     * @return projection's list
     */
    @Query("select t from Tag t")
    <T> List<T> findAll(Class<T> type);
}
