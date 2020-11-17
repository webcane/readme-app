import {Article} from '@app/shared/model/article.model';

export const ARTICLES: Article[] = [
  {
    url: "https://habr.com/ru/post/491540/",
    tags: [
      {
        value: "oop"
      }
    ],
    title: "Сети для начинающего IT-специалиста. Обязательная база",
    preamble: "Часто ли вы задумываетесь – почему что-то сделано так или иначе? Почему у вас микросервисы или монолит, двухзвенка или трехзвенка?"
  },
  {
    url: "https://habr.com/ru/post/263025/",
    tags: [ ],
    title: "Про модель, логику, ООП, разработку и остальное",
    preamble: "Примерно 80% из нас, кто заканчивает университет с какой-либо IT-специальностью, в итоге не становится программистом. Многие устраиваются в техническую поддержку, системными администраторами, мастерами по наладке компьютерных устройств, консультантами-продавцами цифровой техники, менеджерами в it-сферу и так далее."
  },
  {
    url: "https://habr.com/ru/post/453458/",
    tags: [
      {
        value: "svelte"
      }
    ],
    title: "Настоящее реактивное программирование в Svelte 3.0",
    preamble: ""
  },
  {
    url: "https://blog.sensu.io/how-kubernetes-works",
    tags: [
      {
        value: "kubernetes"
      }
    ],
    title: "How Kubernetes works",
    preamble: ""
  }
];
