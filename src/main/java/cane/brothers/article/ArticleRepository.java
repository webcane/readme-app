package cane.brothers.article;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

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

    <T> List<T> findAllByTags_ValueIn(Class<T> type, Collection<String> value);

    /**
     * Find article by unique url
     *
     * @param aUrl the article url
     * @return optional article
     */
    Optional<Article> findByUrl(String aUrl);

    /**
     * Find article by unique slug
     *
     * @param aSlug the article slug
     * @return optional article
     */
    Optional<Article> findBySlug(String aSlug);
}
