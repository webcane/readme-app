import ReadmeApp from './Readme.svelte';

const readmeApp = new ReadmeApp({
	target: document.querySelector('.readme')
});

export default readmeApp;