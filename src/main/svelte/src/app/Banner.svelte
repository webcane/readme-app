<script>
    import {Container} from 'sveltestrap';
    export let show = '';
</script>

<div class="banner" class:d-none="{show !== ''}">
    <Container>
        <h1 class="logo-font">readme</h1>
        <p>A place to share your knowledge</p>
    </Container>
</div>

