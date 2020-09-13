<script>
    import {onMount, setContext} from 'svelte'

    import TagList from './TagList.svelte'
    import Navbar from './Navbar.svelte'
    import Banner from './Banner.svelte'

    import Articles from './Articles.svelte';
    import Editor from './editor/Editor.svelte'
    import Logout from './Logout.svelte'

    export let baseUrl;
    setContext('baseUrl', baseUrl);

    let tags;

    function setTags({detail}) {
        tags = detail.tagSet;
    }

    let selected = '';

    function setMenu({detail}) {
        selected = detail;
    }

    function initArticle() {
        article = emptyArticle;
    }

    let emptyArticle = {url: '', title: '', preambule: '', tags: []};
    let article;

    onMount(() => {
        initArticle();
    });
</script>

<Navbar on:menu='{setMenu}'/>
<div class="home-page">
    <Banner show={selected}/>
    <div class="container page">
        <Row>
            {#if selected === 'editor'}
                <Col md="12">
                    <Editor {article} on:edit='{setMenu|initArticle}'/>
                </Col>
            {:else if selected === 'logout'}
                <Col md="12">
                    <Logout on:menu='{setMenu}'/>
                </Col>
            {:else}
                <Col md="9">
                    <Articles {tags}/>
                </Col>
                <Col md="3">
                    <TagList on:select='{setTags}'/>
                </Col>
            {/if}
        </Row>
    </div>
</div>