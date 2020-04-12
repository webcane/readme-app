package cane.brothers.article;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by cane
 */
@RestController
@RequestMapping("/articles")
@Api(value = "articles")
public class ArticleController {

    @Autowired
    private ArticleService svc;

    @GetMapping
    @ApiOperation(value = "Just to test the sample test api of My App Service")
    public ResponseEntity<List<ArticleView>> getAllArticles() {
        return ResponseEntity.ok(svc.findAll());
    }
}
