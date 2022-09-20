package cane.brothers.article;

import static org.assertj.core.api.Assertions.assertThat;

import cane.brothers.tags.DummyTag;
import cane.brothers.tags.TagService;
import java.util.Collection;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
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
    private ArticleRepository artRepo;

    @Mock
    private TagService tagSvc;
//
    @InjectMocks
    private ArticleService artSvc = new ArticleServiceImpl();

    @Test
    void test_findAll() {
        Mockito.when(artRepo.findAll(ArticleView.class)).thenReturn(DummyArticle.get2Articles());
        List<ArticleView> allArticles = artSvc.findAll();
        log.info(allArticles.toString());

        assertThat(allArticles).isNotNull().hasSize(2);
        assertThat(allArticles.get(0).getUrl()).isSameAs(DummyArticle.getArticle(true).getUrl());
    }

    @Test
    @Disabled("tbi")
    void test_findAll_empty() {
        assertThat(true).isTrue();
    }

    @Test
    void test_findByTagNames_pos() {
        Collection<String> tagNames = List.of(DummyTag.DEFAULT_TAG);
        List<ArticleView> expArts = List.of(DummyArticle.getArticle(true));
        Mockito.when(artRepo.findAllByTags_ValueIn(ArticleView.class, tagNames)).thenReturn(expArts);

        List<ArticleView> allArticles = artSvc.findByTagNames(tagNames);
        log.info(allArticles.toString());

        assertThat(allArticles).isNotNull().hasSize(1);
        assertThat(allArticles.get(0).getTags()).hasSameElementsAs(expArts.get(0).getTags());
    }
}