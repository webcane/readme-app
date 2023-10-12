package cane.brothers.article;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/api/articles")
@Tag(name = "article-controller", description = "The articles API")
@RequiredArgsConstructor
public class ArticleController implements ArticleApi {

    private final ArticleService svc;

    @GetMapping(produces = {"application/json"})
    public ResponseEntity<List<ArticleView>> findAllArticles() {
        List<ArticleView> result = svc.findAll();
        if (result.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/findBy", produces = {"application/json"})
    public ResponseEntity<List<ArticleView>> findArticlesByTagNames(@Valid @RequestParam Collection<String> tags) {
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
    public ResponseEntity<ArticleEntity> postArticle(@Valid @RequestBody ArticleForm request) {
            return new ResponseEntity<>(this.svc.addArticle(request), HttpStatus.CREATED);

    }
}
