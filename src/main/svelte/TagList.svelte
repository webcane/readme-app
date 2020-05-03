<script>
  import { getContext } from 'svelte';
  const baseUrl = getContext('baseUrl');
  
  import { Container, Badge } from 'sveltestrap';
  import Alert from './Alert.svelte';

  let tags = [];
  let tagsPromise;

  import { onMount } from "svelte";
  onMount(() => { tagsPromise = loadTags(); });

  async function loadTags() {
    console.log('load Tags');
    const res = await fetch(baseUrl + "/tags");
    const json = await res.json();

    if (res.ok) {
        //tags = json;
        return json;
    } else {
    	log.console(json);
        throw new Error(json);
    }
  }

  let tagSet = new Set();

  import { createEventDispatcher } from 'svelte';
  const dispatch = createEventDispatcher();

  function onClick(tag) {
    if(tagSet.has(tag.value)) {
      tagSet.delete(tag.value);
    } else {
      tagSet.add(tag.value);
    }
    //tag.selected = !tag.selected;
    //console.log("tag selected: " + tag.selected);
    dispatch("select", {tagSet});
  }
</script>

<div class="sidebar">
  <p>Popular Tags</p>
  <div class="tag-list">
  {#await tagsPromise}
     <!-- <p class="loading">loading...</p> -->
     <Alert message="loading..." color="secondary" />
  {:then tags}
    {#if tags}
        {#each Array.from(tags) as tag}
        <!-- 'default' -->
           <a href="/tags/{tag.value}" class="tag-default tag-pill" 
              on:click|preventDefault='{() => onClick(tag)}'>{tag.value}</a>
              <!-- <Badge pill href="/tags/{tag.value}" color="default"
                on:click|preventDefault='{() => dispatch("select", { tag })}'>{tag.value}</Badge> -->
        {/each}
    {:else}
      <p>No Tags available</p>
    {/if}
  {:catch error}
  	<Alert message="Failed to load Tags" color="danger" />
  {/await}
  </div>
</div>
