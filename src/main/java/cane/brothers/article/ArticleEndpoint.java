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
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * @author mniedre
 */
@Tag(name = "article-controller", description = "The articles API")
public interface ArticleEndpoint {

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "/articles", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "get all articles", tags = {"article", "articles"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "There are all articles found",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = ArticleView.class)))),
                    @ApiResponse(responseCode = "404", description = "Not found"),
                    @ApiResponse(responseCode = "401", description = "Authentication Failure",
                            content = @Content(schema = @Schema(hidden = true)))})
    ResponseEntity<List<ArticleView>> findAllArticles();


    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "/articles/findBy", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "filter articles by tag name", tags = {"article", "articles"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "There are some articles found by tag name",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ArticleView.class)))),
                    @ApiResponse(responseCode = "400", description = "Bad request, For example missing tag name"),
                    @ApiResponse(responseCode = "401", description = "Authentication Failure",
                            content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "404", description = "Not found")})
    ResponseEntity<List<ArticleView>> findArticlesByTagNames(
            @Parameter(explode = Explode.TRUE,
                    name = "tags",
                    description = "Collection of tag names",
                    in = ParameterIn.QUERY,
                    required = true,
                    style = ParameterStyle.FORM)
            @Valid
            @RequestParam Collection<String> tags);


    @RequestMapping(method = RequestMethod.POST, value = "/articles",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "post new article", tags = {"article"},
            responses = {
                    @ApiResponse(responseCode = "201", description = "New article were stored in the DB"),
                    @ApiResponse(responseCode = "400", description = "Bad request, For example second attempt to post the same article"),
                    @ApiResponse(responseCode = "401", description = "Authentication Failure",
                            content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "409", description = "Article already exists")},
            parameters = {
                    @Parameter(name = "Article", required = true)
            }
    )
    ResponseEntity<?> addArticle(@Valid @RequestBody ArticleForm request);


    @RequestMapping(method = RequestMethod.PUT, value = "/articles")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary = "post new article", tags = {"article"},
            responses = {
                    @ApiResponse(responseCode = "202", description = "Article were updated in the DB"),
                    @ApiResponse(responseCode = "400", description = "Bad request, For example second attempt to post the same article"),
                    @ApiResponse(responseCode = "401", description = "Authentication Failure",
                            content = @Content(schema = @Schema(hidden = true))),
                    @ApiResponse(responseCode = "404", description = "Not found by slug or url")},
            parameters = {
                    @Parameter(name = "Article", required = true)
            }
    )
    ResponseEntity<?> updateArticle(@Valid @RequestBody ArticleForm request);
}
