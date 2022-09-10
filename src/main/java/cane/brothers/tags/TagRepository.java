package cane.brothers.tags;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by cane
 */
@Repository
public interface TagRepository extends JpaRepository<TagEntity, Long> {

  /**
   * find all Tags like projections
   *
   * @param type class of projection
   * @param <T>  type of the tag projection
   * @return projection's list
   */
  @Query("select t from TagEntity t")
  <T> List<T> findAll(Class<T> type);
}
