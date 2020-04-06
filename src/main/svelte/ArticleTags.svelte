<script>
  import { Container, Badge } from 'sveltestrap';
  import Tag from './Tag.svelte';
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
        <!-- <Tag class="float-right" tag={tag} /> -->
    {/each}
{/if}