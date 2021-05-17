package cane.brothers.article;

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
public class ArticleController implements ArticleEndpoint {

    private final ArticleService svc;

    @Autowired
    protected ArticleController(ArticleService service) {
        this.svc = service;
    }

    @Override
    public ResponseEntity<List<ArticleView>> findAllArticles() {
        List<ArticleView> result = svc.findAll();
        if (result.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<List<ArticleView>> findArticlesByTagNames(
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

    @Override
    public ResponseEntity<?> addArticle(@Valid @RequestBody ArticleForm request) {
        this.svc.addArticle(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> updateArticle(@Valid @RequestBody ArticleForm request) {
        this.svc.updateArticle(request);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
