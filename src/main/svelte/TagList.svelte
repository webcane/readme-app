<script>
  import { Container, Badge } from 'sveltestrap';
  import { onMount } from "svelte";
  import Tag from './Tag.svelte';

  //const tags = [ 'oop', 'microservice', 'java', 'kubernates', 'azure', 'helm' ];
  let tags = [];
  let gettingQuote = false;

  onMount(() => { loadTags(); });

  async function loadTags() {
  	gettingQuote = true;
    console.log('load Tags');
    const res = await fetch("http://localhost:3000/tags");
    tags = await res.json();
    console.log(tags[0]);
	gettingQuote = false;
  }
//  $: fetch('http://localhost:3000/tags')
//  		.then(r => r.json(); console.log(user))
//  		.then(data => {
//  			items = data;
//  		});
</script>

<div class="sidebar">
  <p>Popular Tags</p>
  <div class="tag-list">
  {#if gettingQuote}
     <p class="loading">loading...</p>
  {:else}
  	 {#each tags as tag}
        <Tag tag={tag} />
     {/each}
  {/if}
  </div>
</div>
