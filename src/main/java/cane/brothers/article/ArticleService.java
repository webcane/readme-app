package cane.brothers.article;

import cane.brothers.tags.Tag;
import cane.brothers.tags.TagForm;
import cane.brothers.tags.TagRepository;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by cane
 */
@Service
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ArticleService {

  private final ArticleRepository artRepo;

  private final TagRepository tagRepo;

  @Autowired
  public ArticleService(ArticleRepository artRepo, TagRepository tagRepo) {
    this.artRepo = artRepo;
    this.tagRepo = tagRepo;
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
        Tag t = getTag(tv);
        a.addTag(t);
      }
    }
    return artRepo.save(a);
  }

  protected Tag getTag(TagForm tv) {
    Tag t = new Tag();
    t.setValue(tv.getValue());
    Example<Tag> example = Example.of(t);
    Optional<Tag> oTag = tagRepo.findOne(example);
    if (oTag.isPresent()) {
      t = oTag.get();
    }
    return t;
  }
}
