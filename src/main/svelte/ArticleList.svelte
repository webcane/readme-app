<script>
  import { Container,Badge } from 'sveltestrap';
  import Article from './Article.svelte';
  import ArticleMeta from './ArticleMeta.svelte';
  import ArticleTags from './ArticleTags.svelte';

  const Articles = [
    {
        title: 'Про модель, логику, ООП, разработку и остальное',
        preamble: 'Часто ли вы задумываетесь – почему что-то сделано так или иначе? Почему у вас микросервисы или монолит, двухзвенка или трехзвенка?',
        link: 'https://habr.com/ru/post/263025/',
        meta: '',
        tags: ['oop']
    },
    {
        title: 'Сети для начинающего IT-специалиста. Обязательная база',
        preamble: 'Примерно 80% из нас, кто заканчивает университет с какой-либо IT-специальностью, в итоге не становится программистом. Многие устраиваются в техническую поддержку, системными администраторами, мастерами по наладке компьютерных устройств, консультантами-продавцами цифровой техники, менеджерами в it-сферу и так далее.',
        link: 'https://habr.com/ru/post/491540/',
        meta: '',
        tags: []
    }
  ];

</script>

{#each Articles as art}
<div class="article-preview">
    <ArticleMeta meta={art.meta} />
    <Article title={art.title} preamble={art.preamble} link={art.link} />
    <ArticleTags tags={art.tags} />
</div>
{/each}