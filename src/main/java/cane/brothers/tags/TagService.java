package cane.brothers.tags;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
}
