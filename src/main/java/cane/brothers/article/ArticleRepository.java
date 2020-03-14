package cane.brothers.article;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by cane
 */
@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    //List<ArticleView> findAllArticles();

    /**
     * find all Articles like projections
     *
     * @param type class of projection
     * @param <T>  type of the address projection
     * @return projection's list
     */
    @Query("select a from Article a")
    <T> List<T> findAll(Class<T> type);
}
