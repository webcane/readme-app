package cane.brothers.tags;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagServiceDefault implements TagService {

  private final TagRepository repo;

  @Override
  public List<TagView> findAll() {
    return repo.findAll(TagView.class);
  }

  @Override
  public TagEntity findTag(TagForm source) {
      TagEntity t = new TagEntity();
      t.setValue(source.getValue());
      Example<TagEntity> example = Example.of(t);
      Optional<TagEntity> oTag = repo.findOne(example);
      if (oTag.isPresent()) {
        t = oTag.get();
      }
      return t;
  }
}
