<script>
  import { Alert } from "sveltestrap";
  export let message;
</script>

<!-- TODO report bug <Alert warning>{message}</Alert> -->
<div class="alert alert-danger" role="alert">
  {message}
</div>
