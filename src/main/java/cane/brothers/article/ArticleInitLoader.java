package cane.brothers.article;

import cane.brothers.tags.TagEntity;
import java.util.ArrayList;
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
public class ArticleInitLoader {

  private static final List<ArticleEntity> allArticles = new ArrayList<>();

  private final ArticleRepository repo;

  @Autowired
  public ArticleInitLoader(ArticleRepository repo) {
    this.repo = repo;
  }

  @EventListener(ContextRefreshedEvent.class)
  public void onApplicationEvent() {
    List<ArticleEntity> existedArticles = this.repo.findAll();
    log.info("Number of articles: " + existedArticles.size());

    if (existedArticles.isEmpty()) {
      allArticles.add(
          getArticle("https://habr.com/ru/post/491540/", "Сети для начинающего IT-специалиста. Обязательная база",
              "Часто ли вы задумываетесь – почему что-то сделано так или иначе? Почему у вас микросервисы или " +
                  "монолит, двухзвенка или трехзвенка?",
              "oop"));
      allArticles.add(getArticle("https://habr.com/ru/post/263025/", "Про модель, логику, ООП, разработку и остальное",
          "Примерно 80% из нас, кто заканчивает университет с какой-либо IT-специальностью, в итоге не становится " +
              "программистом. Многие устраиваются в техническую поддержку, системными администраторами, мастерами по " +
              "наладке компьютерных устройств, консультантами-продавцами цифровой техники, менеджерами в it-сферу и " +
              "так далее."));
      allArticles.add(
          getArticle("https://habr.com/ru/post/453458/", "Настоящее реактивное программирование в Svelte 3.0", "",
              "svelte"));
      allArticles.add(
          getArticle("https://blog.sensu.io/how-kubernetes-works", "How Kubernetes works", "", "kubernetes"));

      log.info("Populate articles");
      for (ArticleEntity a : allArticles) {
        ArticleEntity a2 = repo.save(a);
        log.info("{} added ", a2);
      }
    }
  }

  private static ArticleEntity getArticle(String url, String title, String preamble, String... tags) {
    ArticleEntity a = new ArticleEntity(url, title, preamble);
    addTags(a, tags);
    return a;
  }

  private static void addTags(ArticleEntity a, String[] tags) {
    if (tags != null && tags.length > 0) {
      for (String tag : tags) {
        a.addTag(new TagEntity(tag));
      }
    }
  }
}
