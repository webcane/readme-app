package cane.brothers.article;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author mniedre
 */
@Slf4j
@ExtendWith(MockitoExtension.class)
public class ArticleServiceTest {

    @Mock
    private ArticleRepository repo;

    @InjectMocks
    private ArticleService svc;

    private String tagName = "oop";

    @Test
    void test_findAll() {
        Mockito.when(repo.findAll(ArticleView.class)).thenReturn(DummyArticle.get2Articles());
        List<ArticleView> allArticles = svc.findAll();
        log.info(allArticles.toString());

        assertThat(allArticles).isNotNull();
        assertThat(allArticles.size()).isEqualTo(2);
        assertThat(allArticles.get(0).getUrl()).isSameAs(DummyArticle.getArticle(true).getUrl());
    }

    @Test
    void test_findByTagNames() {
        Collection<String> tagsToSearch = new ArrayList<>();
        tagsToSearch.add(tagName);

        List<ArticleView> searchArticles = new ArrayList<>();
        searchArticles.add(DummyArticle.getArticle(tagName));
        Mockito.when(repo.findAllByTags_ValueIn(ArticleView.class, tagsToSearch))
                .thenReturn(searchArticles);

        List<ArticleView> allArticles = svc.findByTagNames(tagsToSearch);
        log.info(allArticles.toString());

        assertThat(allArticles).isNotNull();
        assertThat(allArticles.size()).isEqualTo(1);
        assertThat(allArticles.get(0).getUrl()).isSameAs(searchArticles.get(0).getUrl());
    }
}
