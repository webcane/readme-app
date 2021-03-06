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

    private static List<Article> allArticles = new ArrayList<>();

    private ArticleRepository repo;

    @Autowired
    public ArticleInitLoader(ArticleRepository repo) {
        this.repo = repo;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        List<Article> existedArticles = this.repo.findAll();
        log.info("Number of articles: " + existedArticles.size());

        if (existedArticles.isEmpty()) {
            allArticles.add(getArticle("https://habr.com/ru/post/491540/", "Сети для начинающего IT-специалиста. Обязательная база",
                    "Часто ли вы задумываетесь – почему что-то сделано так или иначе? Почему у вас микросервисы или монолит, двухзвенка или трехзвенка?", "oop"));
            allArticles.add(getArticle("https://habr.com/ru/post/263025/", "Про модель, логику, ООП, разработку и остальное",
                    "Примерно 80% из нас, кто заканчивает университет с какой-либо IT-специальностью, в итоге не становится программистом. Многие устраиваются в техническую поддержку, системными администраторами, мастерами по наладке компьютерных устройств, консультантами-продавцами цифровой техники, менеджерами в it-сферу и так далее."));
            allArticles.add(getArticle("https://habr.com/ru/post/453458/", "Настоящее реактивное программирование в Svelte 3.0", "", "svelte"));
            allArticles.add(getArticle("https://blog.sensu.io/how-kubernetes-works", "How Kubernetes works", "", "kubernetes"));

            log.info("Populate articles");
            for (Article a : allArticles) {
                Article a2 = repo.save(a);
                log.info("{} added ", a2);
            }
        }
    }

    private static Article getArticle(String url, String title, String preamble, String... tags) {
        Article a = new Article(url, title, preamble);
        addTags(a, tags);
        return a;
    }

    private static void addTags(Article a, String[] tags) {
        if (tags != null && tags.length > 0) {
            for (String tag : tags) {
                a.addTag(new Tag(tag));
            }
        }
    }
}
