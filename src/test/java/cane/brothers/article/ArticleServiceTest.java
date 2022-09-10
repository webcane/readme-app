package cane.brothers.article;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * @author mniedre
 */
@Slf4j
@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    @Mock
    private ArticleRepository repo;

    @InjectMocks
    private ArticleServiceDefault svc;

    @Test
    void test_findAll() {
        Mockito.when(repo.findAll(ArticleView.class)).thenReturn(DummyArticle.get2Articles());
        List<ArticleView> allArticles = svc.findAll();
        log.info(allArticles.toString());

        assertThat(allArticles).isNotNull().hasSize(2);
        assertThat(allArticles.get(0).getUrl()).isSameAs(DummyArticle.getArticle(true).getUrl());
    }
}