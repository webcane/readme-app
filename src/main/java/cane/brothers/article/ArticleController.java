package cane.brothers.article;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.Explode;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.enums.ParameterStyle;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.Collection;
import java.util.List;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by cane
 */
@Slf4j
@RestController
@RequestMapping("/articles")
public class ArticleController {

  private final ArticleService svc;

  @Autowired
  protected ArticleController(ArticleService service) {
    this.svc = service;
  }

  @GetMapping(produces = {"application/json"})
  @Operation(summary = "get all articles", responses = {
      @ApiResponse(responseCode = "200", description = "There are all articles found",
          content = @Content(mediaType = "application/json",
              array = @ArraySchema(schema = @Schema(implementation = ArticleView.class)))),
      @ApiResponse(responseCode = "404", description = "Not found"),
      @ApiResponse(responseCode = "401", description = "Authentication Failure",
          content = @Content(schema = @Schema(hidden = true)))})
  public ResponseEntity<List<ArticleView>> findAllArticles() {
    List<ArticleView> result = svc.findAll();
    if (result.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return ResponseEntity.ok(result);
  }

  @GetMapping(value = "/findBy", produces = {"application/json"})
  @Operation(summary = "filter articles by tag name", responses = {
      @ApiResponse(responseCode = "200", description = "There are some articles found by tag name",
          content = @Content(array = @ArraySchema(schema = @Schema(implementation = ArticleView.class)))),
      @ApiResponse(responseCode = "400", description = "Bad request, For example missing tag name"),
      @ApiResponse(responseCode = "401", description = "Authentication Failure",
          content = @Content(schema = @Schema(hidden = true))),
      @ApiResponse(responseCode = "404", description = "Not found")})
  public ResponseEntity<List<ArticleView>> findArticlesByTagNames(
      @Parameter(explode = Explode.TRUE,
          name = "tags",
          description = "Collection of tag names",
          in = ParameterIn.QUERY,
          required = true,
          style = ParameterStyle.FORM)
      @Valid
      @RequestParam Collection<String> tags) {
    if (CollectionUtils.isEmpty(tags)) {
      log.warn("empty tag request param");
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    List<ArticleView> result = svc.findByTagNames(tags);
    if (result != null && result.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return ResponseEntity.ok(result);
  }

  @PostMapping(consumes = {"application/json", "application/x-www-form-urlencoded"})
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(summary = "post new article", responses = {
      @ApiResponse(responseCode = "201", description = "New article were stored in the DB"),
      @ApiResponse(responseCode = "400", description = "Bad request, For example second attempt to post the same " +
          "article"),
      @ApiResponse(responseCode = "401", description = "Authentication Failure",
          content = @Content(schema = @Schema(hidden = true))),
      @ApiResponse(responseCode = "409", description = "Article already exists")},
      parameters = {
          @Parameter(name = "Article", required = true)
      }
  )
  public ResponseEntity<?> postArticle(@Valid @RequestBody ArticleForm request) {
    try {
      this.svc.addArticle(request);
      return new ResponseEntity<>(HttpStatus.CREATED);
    } catch (Exception ex) {
      log.warn("Unable to add article {}. {}", request, ex.getMessage());
      return new ResponseEntity<>("Unable to publish the article", HttpStatus.BAD_REQUEST);
    }
  }
}
