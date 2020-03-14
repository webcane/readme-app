package cane.brothers.article;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * Created by cane
 */
@Slf4j
@Component
public class ArticleInitLoader implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private ArticleRepository repo;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Article a = new Article(null, "https://habr.com/ru/post/491540/", "Сети для начинающего IT-специалиста. Обязательная база");
        a = repo.save(a);
        log.info("{} added ", a);

        a = new Article(null, "https://habr.com/ru/post/263025/", "Про модель, логику, ООП, разработку и остальное");
        a = repo.save(a);
        log.info("{} added ", a);
    }
}
