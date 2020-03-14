package cane.brothers.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by cane
 */
@RestController
public class ArticleController {

    @Autowired
    private ArticleService svc;

    @RequestMapping("/articles")
    public List<ArticleView> getAllArticles() {
        return svc.findAll();
    }
}
