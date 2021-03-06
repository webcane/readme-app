package cane.brothers.tags;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * Created by cane
 */
@Slf4j
@Component
public class TagInitLoader implements ApplicationListener<ContextRefreshedEvent> {

    private TagRepository repo;

    @Autowired
    public TagInitLoader(TagRepository repo) {
        this.repo = repo;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        List<Tag> existedTags = this.repo.findAll();
        log.info("Number of tags: " + existedTags.size());

        if (existedTags.isEmpty()) {
            log.info("Populate tags");

            List<String> tags = Arrays.asList("java", "docker", "microservice", "helm", "azure");
            for (String tag : tags) {
                addTag(tag);
            }
        }
    }

    private void addTag(String t) {
        Tag a = new Tag(t);
        a = repo.save(a);
        log.info("{} added ", a);
    }
}
