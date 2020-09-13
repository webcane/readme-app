<script>
  import {getContext} from "svelte";

  import Article from "./Article.svelte";
  import ArticleMeta from "./ArticleMeta.svelte";
  import ArticleTags from "./ArticleTags.svelte";
  import Alert from "./Alert.svelte";

  let Articles = [];

  export let tags

  let promise
  $: promise = loadArticles(tags)

  async function loadArticles(tags) {
    let tagName = getTagName(tags);

    let url = getUrl(tagName);
    console.log("load articles by url: " + url);

    const res = await fetch(url);
    if (res.status === 404) {
      throw new Error("There is no articles with selected tag");
    } else if (res.ok) {
      return await res.json();
    } else {
      throw new Error("Failed to load Articles");
    }
  }

  function getTagName(tags) {
    let tagName
    if (tags) {
      tagName = Array.from(tags).toString();
    }
    return tagName;
  }

  function getUrl(tagName) {
    const baseUrl = getContext("baseUrl");
    let url = baseUrl + "/articles";
    if (tagName) {
      url = url + "/findBy?tags=" + tagName;
    }
    return url;
  }
</script>

<!-- <ArticleFilters /> -->

{#await promise}
  <!-- <p class="loading">loading...</p> -->
  <Alert message="loading..." color="secondary"/>
{:then Articles}
  {#if Articles}
    {#each Array.from(Articles) as art}
      <div class="article-preview">
        <ArticleMeta meta={art.meta}/>
        <Article title={art.title} preamble={art.preamble} url={art.url}/>
        <ArticleTags tags={art.tags}/>
      </div>
    {/each}
  {:else}
    <p>No Articles available yet</p>
  {/if}
{:catch error}
  <Alert message={error.message} color="danger"/>
{/await}

<!-- <ArticlePagination /> -->

