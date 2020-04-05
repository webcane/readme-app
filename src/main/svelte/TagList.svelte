<script>
  import { Container, Badge } from 'sveltestrap';
  import { onMount } from "svelte";
  import Tag from './Tag.svelte';

  let tags = [];
  let tagsPromise;

  onMount(() => { tagsPromise = loadTags(); });

  async function loadTags() {
    console.log('load Tags');
    const res = await fetch("http://localhost:3000/tags");
    const json = await res.json();

    if (res.ok) {
        //tags = json;
        return json;
    } else {
    	log.console(json);
        throw new Error(json);
    }
  }
</script>

<div class="sidebar">
  <p>Popular Tags</p>
  <div class="tag-list">
  {#await tagsPromise}
     <p class="loading">loading...</p>
  {:then tags}
    {#if tags}
        {#each Array.from(tags) as tag}
            <Tag tag={tag} />
        {/each}
    {:else}
      <p>No Tags available</p>
    {/if}
  {:catch error}
  	<span class="badge badge-danger">Failed to load Tags</span>
  {/await}
  </div>
</div>
