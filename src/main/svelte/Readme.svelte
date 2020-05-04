<script>
    import { setContext } from 'svelte'  

    import TagList from './TagList.svelte'
    import Navbar from './Navbar.svelte'
    import Banner from './Banner.svelte'  
    import { Col, Container, Row } from "sveltestrap"

    import Articles from './Articles.svelte';
    import NotFound from './NotFound.svelte';

    export let baseUrl;
    setContext('baseUrl', baseUrl);

    let tags;
	function setTags({ detail }) {
		tags = detail.tagSet;
    }
    
    let selected = '';
    function setMenu({ detail }) {
		selected = detail;
    }
</script>

<Navbar on:menu='{setMenu}'/>
<div class="home-page">
    <Banner show={selected}/>
    <div class="container page">
        <Row>

        {#if selected === 'editor'}
            <Col md="12"> editor...</Col>
    
        {:else if selected === 'login'}
            <Col md="12"> login...</Col>

        {:else if selected === 'register'}
            <Col md="12"> register...</Col>
       
        {:else}
            <Col md="9">
                <Articles {tags} />
            </Col>
            <Col md="3">
                <TagList on:select='{setTags}' />
            </Col>
        {/if}
        </Row>
    </div>    
</div>