<script>
  import { Container, Badge } from 'sveltestrap';

  export let tags;
</script>

<!-- TODO tags
        <ul class="tag-list">
            <li class="tag-default tag-pill tag-outline">janwd</li>
        </ul>
-->
{#if tags}
    {#each Array.from(tags) as tag}
        <Badge color="secondary" class="float-right">{tag.value}</Badge>
    {/each}
{/if}