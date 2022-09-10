package cane.brothers.tags;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

/**
 * Created by cane
 */
@Service
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TagService {

  private final TagRepository repo;

  @Autowired
  public TagService(TagRepository repo) {
    this.repo = repo;
  }

  public List<TagView> findAll() {
    return repo.findAll(TagView.class);
  }

  public TagEntity getTag(TagForm tv) {
    TagEntity t = new TagEntity();
    t.setValue(tv.getValue());
    Example<TagEntity> example = Example.of(t);
    Optional<TagEntity> oTag = repo.findOne(example);
    if (oTag.isPresent()) {
      t = oTag.get();
    }
    return t;
  }
}
