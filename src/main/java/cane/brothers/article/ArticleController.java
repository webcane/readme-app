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
import org.springframework.dao.DataIntegrityViolationException;
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
      @ApiResponse(responseCode = "OK", description = "200 OK. All articles found",
          content = @Content(array = @ArraySchema(schema = @Schema(implementation = ArticleView.class)))),
      @ApiResponse(responseCode = "UNAUTHORIZED", description = "401 Unauthorized. Missing jwt token",
          content = @Content(schema = @Schema(hidden = true)))})
  public ResponseEntity<List<ArticleView>> findAllArticles() {
    return ResponseEntity.ok(svc.findAll());
  }

  @GetMapping(value = "/findBy", produces = {"application/json"})
  @Operation(summary = "filter articles by tag name", responses = {
      @ApiResponse(responseCode = "OK", description = "200 Ok. There are some articles found by tag name",
          content = @Content(array = @ArraySchema(schema = @Schema(implementation = ArticleView.class)))),
      @ApiResponse(responseCode = "BAD_REQUEST", description = "400 Bad request. Missing tag name",
          content = @Content(schema = @Schema(hidden = true))),
      @ApiResponse(responseCode = "UNAUTHORIZED", description = "401 Unauthorized. Missing jwt token",
          content = @Content(schema = @Schema(hidden = true))),
      @ApiResponse(responseCode = "NOT_FOUND", description = "404 Not Found. No articles found by given tag name",
          content = @Content(schema = @Schema(hidden = true)))})
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

  @PostMapping(consumes = {"application/json"})
  @Operation(summary = "post new article", responses = {
      @ApiResponse(responseCode = "CREATED", description = "201 Created. New article was stored",
          content = @Content(schema = @Schema(implementation = ArticleView.class))),
      @ApiResponse(responseCode = "BAD_REQUEST", description = "400 Bad request. For example missing or empty url",
          content = @Content(schema = @Schema(hidden = true))),
      @ApiResponse(responseCode = "UNAUTHORIZED", description = "401 Unauthorized. Missing jwt token",
          content = @Content(schema = @Schema(hidden = true))),
      @ApiResponse(responseCode = "CONFLICT", description = "409 Conflict. Article already exists",
          content = @Content(schema = @Schema(hidden = true)))}
  )
  public ResponseEntity<?> postArticle(@Valid @RequestBody
                                       @io.swagger.v3.oas.annotations.parameters.RequestBody(content =
                                       @Content(schema = @Schema(implementation = ArticleView.class)))
                                       ArticleForm request) {
    try {
      this.svc.addArticle(request);
      return new ResponseEntity<>(HttpStatus.CREATED);
    } catch (DataIntegrityViolationException sqlEx) {
      log.warn("Unable to publish article \"{}\". {}", request.getUrl(), sqlEx.getMessage());
      return new ResponseEntity<>("Article already exists", HttpStatus.CONFLICT);
    } catch (Exception ex) {
      log.warn("Unable to add publish {}. {}", request, ex.getMessage());
      return new ResponseEntity<>("Unable to publish the article", HttpStatus.BAD_REQUEST);
    }
  }
}
