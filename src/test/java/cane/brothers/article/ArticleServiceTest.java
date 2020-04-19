package cane.brothers.article;

import cane.brothers.tags.TagServiceTest;
import cane.brothers.tags.TagView;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        List<ArticleView> testArticles = new ArrayList<>();
        ArticleView testArt = new ArticleViewTest();
        testArticles.add(testArt);
        log.info(testArticles.toString());

        Mockito.when(repo.findAll(ArticleView.class)).thenReturn(testArticles);
        List<ArticleView> allArticles = svc.findAll();
        log.info(allArticles.toString());

        assertThat(allArticles).isNotNull();
        assertThat(allArticles.size()).isEqualTo(1);
        assertThat(allArticles.get(0).getUrl()).isSameAs(testArt.getUrl());
    }

    public static class ArticleViewTest implements ArticleView {

        @Override
        public String getUrl() {
            return "http://www.example.com/test/";
        }

        @Override
        public String getTitle() {
            return "test title";
        }

        @Override
        public String getPreamble() {
            return "test preamble";
        }

        @Override
        public Set<TagView> getTags() {
            Set<TagView> tags = new HashSet<>();
            tags.add(new TagServiceTest.TagTest());
            return tags;
        }

        @Override
        public String toString() {
            return "(Url=".concat(getUrl())
                    .concat("; Title=").concat(getTitle())
                    .concat("; Preamble=").concat(getPreamble())
                    .concat(")");
        }
    }
}