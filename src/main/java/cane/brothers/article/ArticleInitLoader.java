package cane.brothers.article;

import cane.brothers.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cane
 */
@Slf4j
@Component
public class ArticleInitLoader implements ApplicationListener<ContextRefreshedEvent> {

    private ArticleRepository repo;

    @Autowired
    public ArticleInitLoader(ArticleRepository repo) {
        this.repo = repo;
    }

    public static List<Article> getArticles() {
        List<Article> allArticlets = new ArrayList<>();
        Article a = new Article("https://habr.com/ru/post/491540/",
                "Сети для начинающего IT-специалиста. Обязательная база",
                "Часто ли вы задумываетесь – почему что-то сделано так или иначе? Почему у вас микросервисы или монолит, двухзвенка или трехзвенка?");
        a.addTag(new Tag("oop"));
        allArticlets.add(a);

        a = new Article("https://habr.com/ru/post/263025/",
                "Про модель, логику, ООП, разработку и остальное",
                "Примерно 80% из нас, кто заканчивает университет с какой-либо IT-специальностью, в итоге не становится программистом. Многие устраиваются в техническую поддержку, системными администраторами, мастерами по наладке компьютерных устройств, консультантами-продавцами цифровой техники, менеджерами в it-сферу и так далее.");
        allArticlets.add(a);
        return allArticlets;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        for (Article a : getArticles()) {
            addArticle(a);
        }
    }

    private void addArticle(Article a) {
        a = repo.save(a);
        log.info("{} added ", a);
    }
}
