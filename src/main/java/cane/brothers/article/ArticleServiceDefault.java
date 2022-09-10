package cane.brothers.article;

import cane.brothers.tags.TagService;
import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleServiceDefault implements ArticleService {

  private final ArticleRepository artRepo;

  private final TagService tagSvc;

  /**
   * @return list of articles. list could be empty.
   */
  @Override
  public List<ArticleView> findAll() {
    return artRepo.findAll(ArticleView.class);
  }

  @Override
  public List<ArticleView> findByTagNames(Collection<String> tagNames) {
    return artRepo.findAllByTags_ValueIn(ArticleView.class, tagNames);
  }

  @Override
  @Transactional
  public ArticleEntity addArticle(ArticleForm request) {
    log.info("Add new article {}", request);
    ArticleEntity a = new ArticleEntity(request.getUrl(), request.getTitle(), request.getPreamble());
    if (hasTags(request)) {
      request.getTags().stream().forEach(tv -> a.addTag(tagSvc.getTag(tv)));
    }
    return artRepo.save(a);
  }

  protected boolean hasTags(ArticleForm request) {
    return request.getTags() != null && !request.getTags().isEmpty();
  }
}
