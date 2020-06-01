<style>
.close {
	font-size: 1rem;
    font-weight: 400;
	padding-left: 5px;
}
</style>

<script>
	// import AutoComplete from './AutoComplete.svelte'
	
  	import { createEventDispatcher } from 'svelte';
  	const dispatch = createEventDispatcher();

	import { getContext } from "svelte";
	
	//import { goto, stores } from '@sapper/app';
	//import ListErrors from '../_components/ListErrors.svelte';
	//import * as api from 'api.js';
	export let article;
	// export let slug;
	let inProgress = false;
	let errors;

	//const { session } = stores();
	function addTag(input) {
		article.tags = [...article.tags, {'value': input.value}];
		input.value = '';
	}

	function remove(index) {
		article.tags = [...article.tags.slice(0, index), ...article.tags.slice(index + 1)];
	}

	async function publish() {
		inProgress = true;
		const baseUrl = getContext("baseUrl");
		let url = baseUrl + "/articles";
		  
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

	function enter(node, callback) {
		function onkeydown(event) {
			if (event.which === 13) callback(node);
		}
		node.addEventListener('keydown', onkeydown);
		return {
			destroy() {
				node.removeEventListener('keydown', onkeydown);
			}
		};
	}

	let availableTags = ["asd", "bsd"];

</script>

<form>
	<fieldset>
		<fieldset class="form-group">
			<input class="form-control form-control-lg" type="text" placeholder="Article URL" bind:value={article.url}>
		</fieldset>

		<fieldset class="form-group">
			<input class="form-control" type="text" placeholder="Article title" bind:value={article.title}>
		</fieldset>

		<fieldset class="form-group">
			<textarea class="form-control" rows="8" placeholder="What's this article about?" bind:value={article.preambule}/>
		</fieldset>

		<fieldset class="form-group">
			<div class="form-control tags-input">
			  {#if article.tags}
				{#each article.tags as tag, i}
					<span class="tag-default tag-pill">
						<!-- <i class="icon ion-close-round" on:click='{() => remove(i)}'/> -->
						<button type="button" class="close" aria-label="Close" on:click='{() => remove(i)}'>
							<span aria-hidden="true">&times;</span>
						</button>
 						{tag.value}
					</span>
				{/each}
			  {/if}	
				<input type="text" class="shadow-none border-0" use:enter={addTag} />
				<!-- <AutoComplete class="shadow-none border-0" items="{availableTags}" /> -->
			</div>
			<input type="text" placeholder="Enter tags" value="asd" data-role="tags-input" class="d-none" />
		</fieldset>

		<button class="btn btn-lg pull-xs-right btn-primary" type="button" disabled={inProgress} on:click={publish}>
			Publish Article
		</button>
	</fieldset>
</form>