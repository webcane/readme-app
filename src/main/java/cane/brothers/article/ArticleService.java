package cane.brothers.article;

import java.util.Collection;
import java.util.List;

/**
 * Created by cane
 */
public interface ArticleService {

  /**
   *
   * @return list of articles. list could be empty.
   */
  List<ArticleView> findAll();

  List<ArticleView> findByTagNames(Collection<String> tagNames);

  ArticleEntity addArticle(ArticleForm request);
}
