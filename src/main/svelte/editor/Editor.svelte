<script>
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
			console.log(response.status);
			throw new Error('Ответ сети был не ok.');
		}
		inProgress = false;
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
							<input class="form-control" type="text" placeholder="Enter tags" use:enter={addTag}>

							<div class="tag-list">
							  {#if article.tags}
								{#each article.tags as tag, i}
									<span class="tag-default tag-pill">
										<i class="ion-close-round" on:click='{() => remove(i)}'/>
										{tag.value}
									</span>
								{/each}
							  {/if}
							</div>
						</fieldset>

						<button class="btn btn-lg pull-xs-right btn-primary" type="button" disabled={inProgress} on:click={publish}>
							Publish Article
						</button>
					</fieldset>
				</form>