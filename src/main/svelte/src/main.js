import ReadmeApp from './app/Readme.svelte';

const readmeApp = new ReadmeApp({
    target: document.querySelector('.readme'),
    props: {
        baseUrl: 'http://localhost:3000/'
    }
});

window.readmeApp = readmeApp;

export default readmeApp;