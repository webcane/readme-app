package cane.brothers.article;

import cane.brothers.tags.DummyTag;
import cane.brothers.tags.TagView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author mniedre
 */
public class DummyArticle implements ArticleView {

    private static int counter;
    private boolean withTags;
    private int id = 0;

    public DummyArticle(boolean withTags) {
        this.withTags = withTags;
        id = ++counter;

    }

    public static List<ArticleView> get2Articles() {
        List<ArticleView> testArticles = new ArrayList<>();
        testArticles.add(getArticle(true));
        testArticles.add(getArticle(false));
        return testArticles;
    }

    public static ArticleView getArticle(boolean withTags) {
        return new DummyArticle(withTags);
    }

    @Override
    public String getUrl() {
        return "http://www.example.com/dummy/";
    }

    @Override
    public String getTitle() {
        return "dummy title " + id;
    }

    @Override
    public String getPreamble() {
        return "dummy preamble";
    }

    @Override
    public Set<TagView> getTags() {
        Set<TagView> tags = new HashSet<>();
        if (withTags) {
            tags.add(new DummyTag());
        }
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
