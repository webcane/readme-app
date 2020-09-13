<script>
    export let title;
    export let preamble;
    export let url;
</script>

<!--  class="preview-link" -->

<h1>{title}</h1>
{#if preamble}
    <p>{preamble}</p>
{/if}
<span><a href={url} target="_blank">Read more...</a></span>
    
