package cane.brothers.article;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

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

    @Test
    void findAll() {
        Mockito.when(repo.findAll(ArticleView.class)).thenReturn(DummyArticle.get2Articles());
        List<ArticleView> allArticles = svc.findAll();
        log.info(allArticles.toString());

        assertThat(allArticles).isNotNull();
        assertThat(allArticles.size()).isEqualTo(2);
        assertThat(allArticles.get(0).getUrl()).isSameAs(DummyArticle.getArticle(true).getUrl());
    }
}