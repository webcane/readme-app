package cane.brothers.article;

import cane.brothers.tags.TagView;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Set;

/**
 * Created by cane
 */
public interface ArticleView {

  @Schema(example = "https://blog.sensu.io/how-kubernetes-works")
  String getUrl();

  @Schema(example = "How Kubernetes works")
  String getTitle();

  @Schema(example = "It’s no secret that the popularity of running containerized applications has exploded over the past several years.")
  String getPreamble();

  Set<TagView> getTags();
}
