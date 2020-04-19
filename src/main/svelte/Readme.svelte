<script>
    import { setContext } from 'svelte';
    import TagList from './TagList.svelte';
    import Navbar from './Navbar.svelte';
    import Banner from './Banner.svelte';
    import ArticleList from './ArticleList.svelte';
    import ArticleFilters from './ArticleFilters.svelte';
    import ArticlePagination from './ArticlePagination.svelte';
    import { Col, Container, Row } from "sveltestrap";

    export let baseUrl;
    setContext('baseUrl', baseUrl);
</script>

<Navbar />
<div class="home-page">
    <Banner />
    <div class="container page">
        <Row>
            <Col md="9">
                <ArticleFilters />
                <ArticleList />
                <ArticlePagination />
            </Col>
            <Col md="3">
                <TagList />
            </Col>
        </Row>
    </div>
</div>