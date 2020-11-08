import ReadmeApp from './app/Readme.svelte';

const readmeApp = new ReadmeApp({
	target: document.querySelector('.readme'),
	props: {
	    baseUrl: 'http://localhost:8080'
	}
});

export default readmeApp;