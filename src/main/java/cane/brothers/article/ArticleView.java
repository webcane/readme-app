package cane.brothers.article;

import cane.brothers.tags.TagView;

import java.util.Set;

/**
 * Created by cane
 */
public interface ArticleView {

    String getUrl();

    String getSlug();

    String getTitle();

    String getPreamble();

    Set<TagView> getTags();
}
