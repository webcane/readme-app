<script>
  import { Container, Badge } from 'sveltestrap';
  export let title;
  export let preamble;
  export let link;
</script>

    <a href="{link}" class="preview-link">
        <h1>{title}</h1>
        {#if preamble}
          <p>{preamble}</p>
        {/if}
        <span>Read more...</span>
    </a>
