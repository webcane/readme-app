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

    let tag;
	function setTags({ detail }) {
		tag = detail.tag;
	}
</script>

<Navbar />
<div class="home-page">
    <Banner />
    <div class="container page">
        <Row>
            <Col md="9">
                <Articles {tag} />
            </Col>
            <Col md="3">
                <TagList on:select='{setTags}' />
            </Col>
        </Row>
    </div>
</div>