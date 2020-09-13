<script>
    import {createEventDispatcher, getContext, onMount} from 'svelte';

    import Tag from './Tag.svelte';
    import Alert from './Alert.svelte';

    const baseUrl = getContext('baseUrl');

    let tags = [];
    let tagsPromise;

    onMount(() => {
        tagsPromise = loadTags();
    });

    async function loadTags() {
        console.log('load Tags');
        const res = await fetch(baseUrl + "/tags");
        const json = await res.json();

        if (res.ok) {
            //tags = json;
            return json;
        } else {
            log.console(json);
            throw new Error(json);
        }
    }

    let tagSet = new Set();

    const dispatch = createEventDispatcher();

    function handleSelect(event) {
        if (tagSet.has(event.detail.tag.value)) {
            tagSet.delete(event.detail.tag.value);
        } else {
            tagSet.add(event.detail.tag.value);
        }
        dispatch("select", {tagSet});
    }
</script>

<div class="sidebar">
    <p>Popular Tags</p>
    <div class="tag-list">
        {#await tagsPromise}
            <!-- <p class="loading">loading...</p> -->
            <Alert message="loading..." color="secondary"/>
        {:then tags}
            {#if tags}
                {#each Array.from(tags) as tag}
                    <Tag {tag} on:select={handleSelect}/>
                {/each}
            {:else}
                <p>No Tags available</p>
            {/if}
        {:catch error}
            <Alert message="Failed to load Tags" color="danger"/>
        {/await}
    </div>
</div>
