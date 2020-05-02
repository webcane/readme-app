package cane.brothers.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
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

    public List<ArticleView> findByTagNames(Collection<String> tagNames) {
        return repo.findAllByTags_ValueIn(ArticleView.class, tagNames);
    }
}
