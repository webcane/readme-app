<script>
  export let message;
  export let color;
</script>

<!-- TODO report bug <Alert warning>{message}</Alert> -->
<div class="alert alert-{color}" role="alert">
  {message}
</div>
