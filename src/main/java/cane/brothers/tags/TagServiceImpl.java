package cane.brothers.tags;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class TagServiceImpl implements TagService {

  @Autowired
  private TagRepository repo;

  @Autowired
  private TagFormToEntityConverter tagConverter;

  @Override
  public List<TagView> findAll() {
    return repo.findAll(TagView.class);
  }

  @Override
  public TagEntity findTag(TagForm source) {
    TagEntity t = tagConverter.convert(source);
    Example<TagEntity> example = Example.of(t);
    return repo.findOne(example).orElse(t);
  }
}
