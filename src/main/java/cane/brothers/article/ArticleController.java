package cane.brothers.article;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by cane
 */
@RestController
@RequestMapping("/articles")
@Api(value = "articles")
public class ArticleController {

    private final ArticleService svc;

    @Autowired
    protected ArticleController(ArticleService service) {
        this.svc = service;
    }

    @GetMapping
    @ApiOperation(value = "Find all articles")
    public ResponseEntity<List<ArticleView>> getAllArticles() {
        return ResponseEntity.ok(svc.findAll());
    }

    @GetMapping(value = "/findBy")
    @ApiOperation(value = "Just to test the sample test api of My App Service")
    public ResponseEntity<List<ArticleView>> findByTag(@RequestParam String tag) {
        List<ArticleView> result = svc.findByTagName(tag);
        if (result != null && !result.isEmpty()) {
            return ResponseEntity.ok(result);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}
