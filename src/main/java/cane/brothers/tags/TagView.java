package cane.brothers.tags;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Created by cane
 */
public interface TagView {

  @Schema(example = "kubernetes")
  String getValue();
}
