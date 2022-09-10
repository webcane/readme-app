package cane.brothers.article;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.Explode;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.enums.ParameterStyle;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.Collection;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface ArticleApi {

  @Operation(summary = "get all articles",  method = "GET", responses = {
      @ApiResponse(responseCode = "200", description = "All founded articles",
          content = @Content(array = @ArraySchema(schema = @Schema(implementation = ArticleView.class)))),
      @ApiResponse(responseCode = "401", description = "Missing jwt token",
          content = @Content(schema = @Schema(hidden = true)))})
  ResponseEntity<List<ArticleView>> findAllArticles();

  @Operation(summary = "filter articles by tag name", method = "GET", responses = {
      @ApiResponse(responseCode = "200", description = "There are some articles found by tag name",
          content = @Content(array = @ArraySchema(schema = @Schema(implementation = ArticleView.class)))),
      @ApiResponse(responseCode = "400", description = "Missing tag name",
          content = @Content(schema = @Schema(hidden = true))),
      @ApiResponse(responseCode = "401", description = "Missing jwt token",
          content = @Content(schema = @Schema(hidden = true))),
      @ApiResponse(responseCode = "404", description = "No articles found by given tag name",
          content = @Content(schema = @Schema(hidden = true)))})
  ResponseEntity<List<ArticleView>> findArticlesByTagNames(
      @Parameter(explode = Explode.TRUE,
          name = "tags",
          description = "Collection of tag names",
          in = ParameterIn.QUERY,
          required = true,
          style = ParameterStyle.FORM) Collection<String> tags);

  @Operation(summary = "post new article",  method = "POST", responses = {
      @ApiResponse(responseCode = "201", description = "New article was stored",
          content = @Content(schema = @Schema(implementation = ArticleForm.class))),
      @ApiResponse(responseCode = "400", description = "For example missing or empty url",
          content = @Content(schema = @Schema(hidden = true))),
      @ApiResponse(responseCode = "401", description = "Missing jwt token",
          content = @Content(schema = @Schema(hidden = true))),
      @ApiResponse(responseCode = "409", description = "Article already exists",
          content = @Content(schema = @Schema(hidden = true)))}
  )
  ResponseEntity<String> postArticle(@RequestBody(content = @Content(schema = @Schema(implementation = ArticleForm.class)))
                                ArticleForm request);
}
