package cane.brothers.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by cane
 */
@Service
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ArticleService {

    private ArticleRepository repo;

    @Autowired
    public ArticleService(ArticleRepository repo) {
        this.repo = repo;
    }

    public List<ArticleView> findAll() {
        return repo.findAll(ArticleView.class);
    }

    public List<ArticleView> findByTagName(String tagName) {
        return repo.findAllByTags_Value(ArticleView.class, tagName);
    }
}
