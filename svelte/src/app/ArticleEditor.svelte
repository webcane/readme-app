<script>
	import { onMount, getContext, createEventDispatcher } from "svelte";

	const dispatch = createEventDispatcher();
    let inProgress = false;

    export let article;

	function publish() {
	    console.log("article to publish: " + JSON.stringify(article));
	    console.log('article posted');

        dispatch('edit', '');
	}
</script>

<form>
	<fieldset>
		<button class="btn btn-lg pull-xs-right btn-primary" type="button" disabled={inProgress} on:click={publish}>
			Publish Article
		</button>
	</fieldset>
</form>