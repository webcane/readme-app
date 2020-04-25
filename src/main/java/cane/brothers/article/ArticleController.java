package cane.brothers.article;

import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
            @ApiResponse(code = 200, message = "There are some articles found by tag name"),
            @ApiResponse(code = 400, message = "There is no result")})
    public ResponseEntity<List<ArticleView>> findAllArticles() {
        List<ArticleView> result = svc.findAll();
        if (result.isEmpty()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/findBy")
    @ApiOperation(value = "Filter articles by tag name", response = ArticleView.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "There are some articles found by tag name"),
            @ApiResponse(code = 400, message = "There is no result"),
            @ApiResponse(code = 404, message = "Bad request, For example missing tag name")})
    public ResponseEntity<List<ArticleView>> findArticlesByTag(@ApiParam("Tag name") @RequestParam String tag) {
        if (StringUtils.isEmpty(tag)) {
            log.warn("empty tag request param");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        List<ArticleView> result = svc.findByTagName(tag);
        if (result != null && result.isEmpty()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(result);
    }
}
