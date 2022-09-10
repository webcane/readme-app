package cane.brothers.article;

import cane.brothers.tags.TagEntity;
import cane.brothers.tags.TagForm;
import cane.brothers.tags.TagService;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by cane
 */
@Service
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ArticleService {

  private final ArticleRepository artRepo;

  private final TagService tagSvc;

  @Autowired
  public ArticleService(ArticleRepository artRepo, TagService tagSvc) {
    this.artRepo = artRepo;
    this.tagSvc = tagSvc;
  }

  /**
   *
   * @return list of articles. list could be empty.
   */
  public List<ArticleView> findAll() {
    return artRepo.findAll(ArticleView.class);
  }

  public List<ArticleView> findByTagNames(Collection<String> tagNames) {
    return artRepo.findAllByTags_ValueIn(ArticleView.class, tagNames);
  }

  @Transactional
  public ArticleEntity addArticle(ArticleForm request) {
    ArticleEntity a = new ArticleEntity(request.getUrl(), request.getTitle(), request.getPreamble());
    if (request.getTags() != null && request.getTags().isEmpty()) {
      for (TagForm tv : request.getTags()) {
        TagEntity t = tagSvc.getTag(tv);
        a.addTag(t);
      }
    }
    return artRepo.save(a);
  }
}
