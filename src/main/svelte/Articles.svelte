<script>
    //import ArticleList from './ArticleList.svelte'
    import { getContext } from "svelte";
    const baseUrl = getContext("baseUrl");

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
    $: {
        let tagName
        if(params) {
            tagName = params.tagName
        }
        promise = loadArticles(tagName);
    }

    async function loadArticles(tagName) {
        let url;
        console.log(tagName);
        if (tagName) {
            url = baseUrl + "/articles/findBy?tag=" + tagName;
        } else {
            url = baseUrl + "/articles";
        }
        const res = await fetch(url);

        if (res.status === 404) {
            throw new Error("There is no articles with selected tag");
        } else if (res.ok) {
            const json = await res.json();
            return json;
        } else {
            throw new Error("Failed to load Articles");
        }
    }
</script>

<!-- <ArticleFilters /> -->

<!-- <ArticleList params={promise}/> -->

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
  <Alert message={error.message} />
{/await}

<!-- <ArticlePagination /> -->

