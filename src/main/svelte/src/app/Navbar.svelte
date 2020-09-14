<script>
    import {
        Container,
        Nav,
        Navbar,
        NavbarBrand,
        NavItem,
        NavLink
    } from 'sveltestrap';
    import {createEventDispatcher, getContext, onMount} from "svelte";

    const dispatch = createEventDispatcher();
    let baseUrl = getContext("baseUrl");
    let authorized = false;
    let inProgress = false;

    onMount(checkUser);

    async function checkUser() {
        const res = await fetch(baseUrl + "/user");
        const json = await res.json();

        if (res.ok) {
            authorized = true;
            console.log('check User: ' + json.name);
        } else {
            authorized = false;
            log.console(json);
            throw new Error(json);
        }
    }

    async function logout(event) {
        inProgress = true;
        const response = await fetch(baseUrl + "/logout", {
            method: 'POST'
        });
        const text = await response.text();
        if (response.ok) {
            authorized = false;
            dispatch('menu', 'logout');
        } else {
            throw new Error(text);
        }
        inProgress = false;
    }
</script>

<Navbar light expand="sm">
    <Container>
        <NavbarBrand href="/">readme app</NavbarBrand>
        <Nav class="ml-auto" navbar>
            {#if authorized}
                <NavItem>
                    <!-- /editor -->
                    <NavLink href="#" on:click={() => dispatch('menu', 'editor')}>New</NavLink>
                </NavItem>
                <NavItem>
                    <!-- /logout -->
                    <NavLink href="#" on:click={logout} disabled='{inProgress}'>Logout</NavLink>
                </NavItem>
            {:else}
                <NavItem>
                    <NavLink href="/svc/oauth2/authorization/github" title="Login with GitHub">Sign in</NavLink>
                </NavItem>
            {/if}
        </Nav>
    </Container>
</Navbar>