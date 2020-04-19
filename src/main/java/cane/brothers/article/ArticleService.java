package cane.brothers.article;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by cane
 */
@Service
@RequiredArgsConstructor
public class ArticleService {

    private ArticleRepository repo;

    public List<ArticleView> findAll() {
        return repo.findAll(ArticleView.class);
    }

    public List<ArticleView> findByTagName(String tagName) {
        return repo.findAllByTags_Value(ArticleView.class, tagName);
    }
}
