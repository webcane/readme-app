package cane.brothers.article;

import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * Created by cane
 */
@Slf4j
@RestController
@RequestMapping("/articles")
@Api(value = "Endpoint for managing articles")
public class ArticleController {

    private final ArticleService svc;

    @Autowired
    protected ArticleController(ArticleService service) {
        this.svc = service;
    }

    @GetMapping
    @ApiOperation(value = "Get all articles", response = ArticleView.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "There are all articles found"),
            @ApiResponse(code = 404, message = "There is no result")})
    public ResponseEntity<List<ArticleView>> findAllArticles() {
        List<ArticleView> result = svc.findAll();
        if (result.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/findBy")
    @ApiOperation(value = "Filter articles by tag name", response = ArticleView.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "There are some articles found by tag name"),
            @ApiResponse(code = 400, message = "Bad request, For example missing tag name"),
            @ApiResponse(code = 404, message = "There is no result")})
    public ResponseEntity<List<ArticleView>> findArticlesByTagNames(@ApiParam("Tag name") @RequestParam Collection<String> tags) {
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "post new article", httpMethod = "POST")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "New article were stored in the DB"),
            @ApiResponse(code = 400, message = "Bad request, For example second attempt to post the same article")})
    public ResponseEntity<Void> postArticle(@ApiParam("Article") @Valid @RequestBody ArticleForm request) {
        try {
            this.svc.addArticle(request);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        catch (Exception ex) {
            log.warn("Unable to add article {}. {}", request, ex.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
