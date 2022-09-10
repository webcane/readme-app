package cane.brothers.article;

import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by cane
 */
@Repository
public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {

  /**
   * find all Articles like projections
   *
   * @param type class of projection
   * @param <T>  type of the address projection
   * @return projection's list
   */
  @Query("select a from ArticleEntity a")
  <T> List<T> findAll(Class<T> type);

  <T> List<T> findAllByTags_ValueIn(Class<T> type, Collection<String> value);
}
