import ReadmeApp from './Readme.svelte';

const app = new ReadmeApp({
	target: document.querySelector('.readme'),
	props: {
		name: 'world'
	}
});

export default app;