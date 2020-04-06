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

    @Autowired
    private TagRepository repo;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        List<String> tags = Arrays.asList("java", "kubernetes", "docker", "microservice", "helm", "azure");
        for (String tag : tags) {
            addTag(tag);
        }
    }

    private void addTag(String t) {
        Tag a = new Tag(t);
        a = repo.save(a);
        log.info("{} added ", a);
    }
}
