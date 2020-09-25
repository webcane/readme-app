<script>
	import Tags from "svelte-tags-input";
	import { createEventDispatcher } from 'svelte';
	import { getContext } from "svelte";
	import { onMount } from "svelte";
	//import { goto, stores } from '@sapper/app';
	//import ListErrors from '../_components/ListErrors.svelte';
	//import * as api from 'api.js';

	const dispatch = createEventDispatcher();
	let inputTags = "";

	let availableTags = [];
	let tagsPromise = Promise.resolve([]);

	export let article;
	// export let slug;
	let inProgress = false;
	let errors;

	const baseUrl = getContext('baseUrl');

	onMount(() => { 
		tagsPromise = loadTags(); 
		console.log('promise' + JSON.stringify(tagsPromise));
		tagsPromise.then(result => {
			availableTags = result.map(function(tag) {return tag.value;});
			console.log('available Tags: ' + availableTags);
		});
	});

	function handleTags(event) {
		const tmp_tags = event.detail.tags;
		article.tags = tmp_tags.toString().split(',').map(function(t) {
			let tag = new Object();
			tag.value = t.trim();
			return tag;
		});
	}

	async function loadTags() {
		console.log('preload Tags');
		const res = await fetch(baseUrl + "/tags");
		const json = await res.json();

		if (res.ok) {
			return json;
		} else {
			console.log(json);
			throw new Error(json);
		}
	}

	async function publish() {
		inProgress = true;
		let url = baseUrl + "/articles";

		console.log("article to publish: " + JSON.stringify(article));
		  
		// const response = await (slug
		// 	? api.put(`articles/${slug}`, { article }, $session.user && $session.user.token)
		// 	: api.post('articles', { article }, $session.user && $session.user.token));
		// if (response.article) {
		// 	//goto(`/article/${response.article.slug}`);
		// }
		const response = await fetch(url, {
				method: 'POST',
				body: JSON.stringify(article),
				headers: {
					'Content-Type': 'application/json'
				}
			}
		);
		//let result = await response.json();
		if (!response.ok) {
			console.log("articles was not posted");
			console.log(response.status);
			throw new Error('There is no positive network answer');
		}
		inProgress = false;

		// go to home page
		dispatch("edit", '');
	}
</script>

<style>
.tags-input :global(.svelte-tags-input-tag) {
    background:blue;
}

.tags-input :global(.svelte-tags-input-layout) {
    background:yellow;
}
</style>

<form>
	<fieldset>
		<fieldset class="form-group">
			<input class="form-control form-control-lg" type="text" placeholder="Article URL" bind:value={article.url}>
		</fieldset>

		<fieldset class="form-group">
			<input class="form-control" type="text" placeholder="Article title" bind:value={article.title} />
		</fieldset>

		<fieldset class="form-group">
			<textarea class="form-control" rows="8" placeholder="What's this article about?" bind:value={article.preambule}/>
		</fieldset>

		<fieldset class="form-group tags-input">
			<Tags on:tags={handleTags} autoComplete={availableTags} onlyUnique={true} bind:tags={inputTags} />
		</fieldset>

		<button class="btn btn-lg pull-xs-right btn-primary" type="button" disabled={inProgress} on:click={publish}>
			Publish Article
		</button>
	</fieldset>
</form>