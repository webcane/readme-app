package cane.brothers.article;

import cane.brothers.tags.Tag;
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

        Article a = new Article("https://habr.com/ru/post/491540/",
                "Сети для начинающего IT-специалиста. Обязательная база",
                "Часто ли вы задумываетесь – почему что-то сделано так или иначе? Почему у вас микросервисы или монолит, двухзвенка или трехзвенка?");
        a.addTag(new Tag("oop"));
        a = repo.save(a);
        log.info("{} added ", a);

        a = new Article("https://habr.com/ru/post/263025/",
                "Про модель, логику, ООП, разработку и остальное",
                "Примерно 80% из нас, кто заканчивает университет с какой-либо IT-специальностью, в итоге не становится программистом. Многие устраиваются в техническую поддержку, системными администраторами, мастерами по наладке компьютерных устройств, консультантами-продавцами цифровой техники, менеджерами в it-сферу и так далее.");
        a = repo.save(a);
        log.info("{} added ", a);
    }
}
