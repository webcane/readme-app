<script>
  import { Container, Badge } from 'sveltestrap';
  import Tag from './Tag.svelte';
  const tags = [ 'oop', 'microservice', 'java', 'kubernates', 'azure', 'helm' ];
</script>

<div class="sidebar">
  <p>Popular Tags</p>
  <div class="tag-list">
      {#each tags as tag}
          <Tag tag={tag} />
      {/each}
  </div>
</div>
