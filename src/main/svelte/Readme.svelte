<script>

    import { setContext } from 'svelte'
    import Router from 'svelte-spa-router'
    

    import TagList from './TagList.svelte'
    import Navbar from './Navbar.svelte'
    import Banner from './Banner.svelte'  
    import { Col, Container, Row } from "sveltestrap"

    import Articles from './Articles.svelte';
    import NotFound from './NotFound.svelte';

    const routes = {
        '/' : Articles,
        '/tags/:tagName' : Articles, 
        // Catch-all, must be last
        '*' : NotFound 
    }

    export let baseUrl;
    setContext('baseUrl', baseUrl);

  function routeLoaded(event) {
    console.log("loaded route: \'" + event.detail.location + "\'");
  }
</script>

<Navbar />
<div class="home-page">
    <Banner />
    <div class="container page">
        <Row>
            <Col md="9">
                <Router {routes} on:routeLoaded={routeLoaded} />
            </Col>
            <Col md="3">
                <TagList />
            </Col>
        </Row>
    </div>
</div>