<script>
  import { Badge } from 'sveltestrap'
  import Router from 'svelte-spa-router'
  import {link, push, pop, replace, location, querystring} from 'svelte-spa-router'

  export let tag;
  console.log(tag);
</script>

<!-- <Badge href="/tags/{tag}" color="secondary">{tag}</Badge> -->
<a href="/tags/{tag.value}" use:link class="tag-default tag-pill">{tag.value}</a>