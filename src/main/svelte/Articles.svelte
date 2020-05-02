<script>
    //import ArticleList from './ArticleList.svelte'
    import { getContext } from "svelte";
    import { Container, Badge } from "sveltestrap";
    import Article from "./Article.svelte";
    import ArticleMeta from "./ArticleMeta.svelte";
    import ArticleTags from "./ArticleTags.svelte";
    import Alert from "./Alert.svelte";
    //import { onMount } from "svelte";

    import ArticleFilters from './ArticleFilters.svelte'
    import ArticlePagination from './ArticlePagination.svelte'

    let Articles = [];
    
    let promise
    export let params
    $: promise = loadArticles(params)

    async function loadArticles(params) {
      let tagName = getTagName(params);

      let url = getUrl(tagName);
      console.log("load articles by url: " + url);

      const res = await fetch(url);
      if (res.status === 404) {
        throw new Error("There is no articles with selected tag");
      } 
      else if (res.ok) {
        return await res.json();
      } 
      else {
        throw new Error("Failed to load Articles");
      }
    }

    function getTagName(params) {
      let tagName
      if(params) {
        tagName = params.tagName
      }
      return tagName;
    }

    function getUrl(tagName) {
      const baseUrl = getContext("baseUrl");
      let url = baseUrl + "/articles";
      if (tagName) {
        url = url + "/findBy?tag=" + tagName;
      }
      return url;
    }
</script>

<!-- <ArticleFilters /> -->

<!-- <ArticleList params={promise}/> -->

{#await promise}
  <!-- <p class="loading">loading...</p> -->
  <Alert message="loading..." color="secondary" />
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
  <Alert message={error.message} color="danger" />
{/await}

<!-- <ArticlePagination /> -->

