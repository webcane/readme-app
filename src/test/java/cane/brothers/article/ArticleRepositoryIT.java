package cane.brothers.article;

import cane.brothers.tags.Tag;
import cane.brothers.tags.TagView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * @author mniedre
 */
@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ArticleRepositoryIT {

    private ArticleRepository repo;

    private String tagName_pos = "oop";
    private String tagName_neg = "JavaScript";

    @Autowired
    public ArticleRepositoryIT(ArticleRepository repo) {
        this.repo = repo;
    }

    @BeforeEach
    public void setUp() {
        Article testArticle = new Article("http://www.example.com/test/", "test-slug", "test title", "test preamble");
        testArticle.addTag(new Tag(tagName_pos));
        repo.save(testArticle);
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

    private static boolean hasTag(Set<TagView> tags, String tagName) {
        for (TagView tag : tags) {
            return tag.getValue().equals(tagName);
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

    @Test
    public void test_findBySlug_pos() {
        // when
        Optional<Article> artBySlug = repo.findBySlug("test-slug");

        // then
        assertThat(artBySlug.isPresent()).isTrue();
    }

    @Test
    public void test_findBySlug_neg() {
        // when
        Optional<Article> artBySlug = repo.findBySlug("unknown-slug");

        // then
        assertThat(artBySlug.isPresent()).isFalse();
    }

    @Test
    public void test_findByUrl_pos() {
        // when
        Optional<Article> artByUrl = repo.findByUrl("http://www.example.com/test/");

        // then
        assertThat(artByUrl.isPresent()).isTrue();
    }

    @Test
    public void test_findByUrl_neg() {
        // when
        Optional<Article> artByUrl = repo.findBySlug("http://www.example.com/unknown");

        // then
        assertThat(artByUrl.isPresent()).isFalse();
    }
}
