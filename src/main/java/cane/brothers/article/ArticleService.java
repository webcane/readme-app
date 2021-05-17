package cane.brothers.article;

import cane.brothers.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import cane.brothers.tags.Tag;
import cane.brothers.tags.TagForm;
import cane.brothers.tags.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by cane
 */
@Slf4j
@Service
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ArticleService {

    private ArticleRepository artRepo;

    private TagRepository tagRepo;

    @Autowired
    public ArticleService(ArticleRepository artRepo, TagRepository tagRepo) {
        this.artRepo = artRepo;
        this.tagRepo = tagRepo;
    }

    public List<ArticleView> findAll() {
        return artRepo.findAll(ArticleView.class);
    }

    public List<ArticleView> findByTagNames(Collection<String> tagNames) {
        return artRepo.findAllByTags_ValueIn(ArticleView.class, tagNames);
    }

    @Transactional
    public void addArticle(ArticleForm request) {
        Article a = updateArticleInternal(request, new Article());
        artRepo.save(a);
    }

    /**
     * To update the article we will search it by slug or url.
     * It means not allowed to change slug and url at the same time.
     *
     * @param request
     * @return true if article were updated successfully
     */
    @Transactional
    public void updateArticle(ArticleForm request) {
        Article articleUpdated;
        Optional<Article> articleOptional = artRepo.findBySlug(request.getSlug());

        // first search by url
        if(articleOptional.isPresent()) {
            articleUpdated = updateArticleInternal(request, articleOptional.get());
        }

        else {
            // then search by url
            articleOptional = artRepo.findByUrl(request.getUrl());

            if(articleOptional.isPresent()) {
                articleUpdated = updateArticleInternal(request, articleOptional.get());
            }

            else {
                throw new ResourceNotFoundException("Article", "url", request.getUrl());
            }
        }

        artRepo.save(articleUpdated);
    }

    private Article updateArticleInternal(ArticleForm request, Article articleOrig) {
        articleOrig.setUrl(request.getUrl());
        articleOrig.setSlug(request.getSlug());
        articleOrig.setTitle(request.getTitle());
        articleOrig.setPreamble(request.getPreamble());

        articleOrig.getTags().clear();
        Set<TagForm> reqTags = request.getTags();

        if (CollectionUtils.isNotEmpty(reqTags)) {
            for (TagForm tv : reqTags) {
                Tag t = getTag(tv);
                articleOrig.addTag(t);
            }
        }

        return articleOrig;
    }

    private Tag getTag(TagForm tv) {
        Tag t = new Tag();
        t.setValue(tv.getValue());
        Example<Tag> example = Example.of(t);
        Optional<Tag> oTag = tagRepo.findOne(example);
        if (oTag.isPresent()) {
            t = oTag.get();
        }
        return t;
    }
}
