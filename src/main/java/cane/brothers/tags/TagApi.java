package cane.brothers.tags;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface TagApi {

  @Operation(summary = "get all tags", method = "GET", responses = {
      @ApiResponse(responseCode = "200", description = "All founded tags",
          content = @Content(array = @ArraySchema(schema = @Schema(implementation = TagView.class)))),
      @ApiResponse(responseCode = "401", description = "Missing jwt token",
          content = @Content(schema = @Schema(hidden = true)))})
  ResponseEntity<List<TagView>> getAllTags();
}
