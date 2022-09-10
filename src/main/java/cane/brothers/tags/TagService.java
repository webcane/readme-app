package cane.brothers.tags;

import java.util.List;
import java.util.Set;

/**
 * Created by cane
 */
public interface TagService {

  List<TagView> findAll();

  default boolean hasTags(Set<TagForm> tags) {
    return tags != null && !tags.isEmpty();
  }

  TagEntity findTag(TagForm tf);
}
