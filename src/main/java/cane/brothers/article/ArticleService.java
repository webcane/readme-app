package cane.brothers.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by cane
 */
@Service
public class ArticleService {

    @Autowired
    private ArticleRepository repo;

    public List<ArticleView> findAll() {
        return repo.findAll(ArticleView.class);
    }
}
