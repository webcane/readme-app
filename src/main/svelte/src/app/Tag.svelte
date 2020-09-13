<script>
    export let tag;

    import {createEventDispatcher} from 'svelte';

    const dispatch = createEventDispatcher();

    let active;

    function onClick(tag) {
        active = (active ? '' : tag.value);
        dispatch("select", {tag});
    }
</script>

<a href="/tags/{tag.value}" class="tag-default tag-pill" class:tag-success="{active === tag.value}"
   on:click|preventDefault='{() => onClick(tag)}'>{tag.value}</a>