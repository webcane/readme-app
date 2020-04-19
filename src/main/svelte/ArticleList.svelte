<script>
  import { getContext } from 'svelte';
  import { Container,Badge } from 'sveltestrap';
  import Article from './Article.svelte';
  import ArticleMeta from './ArticleMeta.svelte';
  import ArticleTags from './ArticleTags.svelte';
  import Alert from './Alert.svelte';
  import { onMount } from "svelte";

//  const Articles = [
//    {
//        title: 'Про модель, логику, ООП, разработку и остальное',
//        preamble: 'Часто ли вы задумываетесь – почему что-то сделано так или иначе? Почему у вас микросервисы или монолит, двухзвенка или трехзвенка?',
//        link: 'https://habr.com/ru/post/263025/',
//        meta: '',
//        tags: ['oop']
//    },
//    {
//        title: 'Сети для начинающего IT-специалиста. Обязательная база',
//        preamble: 'Примерно 80% из нас, кто заканчивает университет с какой-либо IT-специальностью, в итоге не становится программистом. Многие устраиваются в техническую поддержку, системными администраторами, мастерами по наладке компьютерных устройств, консультантами-продавцами цифровой техники, менеджерами в it-сферу и так далее.',
//        link: 'https://habr.com/ru/post/491540/',
//        meta: '',
//        tags: []
//    }
//  ];

  const baseUrl = getContext('baseUrl');

  let Articles = [];
  let promise;

  onMount(() => { promise = loadArticles(); });

    async function loadArticles() {
      console.log('load Articles');
      const res = await fetch(baseUrl + "/articles");
      const json = await res.json();
      console.log(json);

      if (res.ok) {
          return json;
      } else {
      	log.console(json);
        throw new Error(json);
      }
    }
</script>

  {#await promise}
     <p class="loading">loading...</p>
  {:then Articles}
    {#if Articles}
        {#each Array.from(Articles) as art}
            <div class="article-preview">
                <ArticleMeta meta={art.meta} />
                <Article title={art.title} preamble={art.preamble} link={art.link} />
                <ArticleTags tags={art.tags} />
            </div>
        {/each}
    {:else}
      <p>No Articles available yet</p>
    {/if}
  {:catch error}
    <Alert message="Failed to load Articles" />
  {/await}