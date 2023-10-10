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

  @Operation(summary = "get all articles", tags = {"article", "articles"},
      responses = {
          @ApiResponse(responseCode = "200", description = "There are all articles found",
              content = @Content(mediaType = "application/json",
                  array = @ArraySchema(schema = @Schema(implementation = ArticleView.class)))),
          @ApiResponse(responseCode = "404", description = "Not found"),
          @ApiResponse(responseCode = "401", description = "Authentication failure",
              content = @Content(schema = @Schema(hidden = true)))})
  ResponseEntity<List<ArticleView>> findAllArticles();

  @Operation(summary = "filter articles by tag name", tags = {"article", "articles"},
      responses = {
          @ApiResponse(responseCode = "200", description = "There are some articles found by tag name",
              content = @Content(array = @ArraySchema(schema = @Schema(implementation = ArticleView.class)))),
          @ApiResponse(responseCode = "400", description = "Bad request, For example missing tag name"),
          @ApiResponse(responseCode = "401", description = "Authentication failure",
              content = @Content(schema = @Schema(hidden = true))),
          @ApiResponse(responseCode = "404", description = "Not found")})
  ResponseEntity<List<ArticleView>> findArticlesByTagNames(@Parameter(explode = Explode.TRUE,
      name = "tags",
      description = "Collection of tag names",
      in = ParameterIn.QUERY,
      required = true,
      style = ParameterStyle.FORM) Collection<String> tags);

  @Operation(summary = "post new article", tags = {"article"},
      responses = {
          @ApiResponse(responseCode = "201", description = "New article were stored in the DB"),
          @ApiResponse(responseCode = "400", description = "Bad request, For example second attempt to post the same article"),
          @ApiResponse(responseCode = "401", description = "Authentication failure",
              content = @Content(schema = @Schema(hidden = true))),
          @ApiResponse(responseCode = "409", description = "Article already exists")},
      parameters = {
          @Parameter(name = "Article", required = true)
      }
  )
  ResponseEntity<ArticleEntity> postArticle(
      @RequestBody(content = @Content(schema = @Schema(implementation = ArticleForm.class)))
      ArticleForm request);
}
