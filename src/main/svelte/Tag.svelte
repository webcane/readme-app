<script>
  import { Badge } from 'sveltestrap';
  export let tag;
  console.log({tag});
</script>

<!-- <Badge href="/tags/{tag}" color="secondary">{tag}</Badge> -->
<a href="." class="tag-default tag-pill">{tag.value}</a>