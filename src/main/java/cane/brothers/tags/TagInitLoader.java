package cane.brothers.tags;

import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Created by cane
 */
@Slf4j
@Component
@Profile("dev")
public class TagInitLoader {

  private TagRepository repo;

  @Autowired
  public TagInitLoader(TagRepository repo) {
    this.repo = repo;
  }

  @EventListener(ContextRefreshedEvent.class)
  public void onApplicationEvent() {
    List<TagEntity> existedTags = this.repo.findAll();
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
    TagEntity a = new TagEntity(t);
    a = repo.save(a);
    log.info("{} added ", a);
  }
}
