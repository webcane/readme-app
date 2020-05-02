package cane.brothers.article;

import cane.brothers.tags.Tag;
import cane.brothers.tags.TagView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author mniedre
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class ArticleRepositoryIT {

    private ArticleRepository repo;

    private String tagName_pos = "oop";
    private String tagName_neg = "JavaScript";

    @Autowired
    public ArticleRepositoryIT(ArticleRepository repo) {
        this.repo = repo;
    }

    @Before
    public void setUp() throws Exception {
        Article testArticle = new Article("http://www.example.com/test/", "test title", "test preamble");
        testArticle.addTag(new Tag(tagName_pos));
        testArticle = repo.save(testArticle);
    }

    @Test
    public void test_findByTagName_pos() {
        // when
        Collection<String> tagsToSearch = new ArrayList<>();
        tagsToSearch.add(tagName_pos);
        List<ArticleView> artsByTags = repo.findAllByTags_ValueIn(ArticleView.class, tagsToSearch);

        // then
        assertThat(artsByTags).isNotNull();

        for (ArticleView art : artsByTags) {
            Set<TagView> tags = art.getTags();

            assertThat(tags).isNotEmpty();
            assertThat(hasTag(tags, tagName_pos)).isTrue();
        }
    }

    private static final boolean hasTag(Set<TagView> tags, String tagName) {
        for (TagView tag : tags) {
            tag.getValue().equals(tagName);
            return true;
        }
        return false;
    }

    @Test
    public void test_findByTagName_neg() {
        // when
        Collection<String> tagsToSearch = new ArrayList<>();
        tagsToSearch.add(tagName_neg);
        List<ArticleView> artsByTags = repo.findAllByTags_ValueIn(ArticleView.class, tagsToSearch);

        // then
        assertThat(artsByTags).isNotNull();
        assertThat(artsByTags).isEmpty();
    }
}