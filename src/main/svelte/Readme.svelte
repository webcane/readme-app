<script>
    import Tags from './TagSidebar.svelte';
    import Navbar from './Navbar.svelte';
    import Banner from './Banner.svelte';
    import ArticleList from './ArticleList.svelte';
    import ArticleFilters from './ArticleFilters.svelte';
    import ArticlePagination from './ArticlePagination.svelte';
    import { Col, Container, Row } from "sveltestrap";
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
                <Tags />
            </Col>
        </Row>
    </div>
</div>