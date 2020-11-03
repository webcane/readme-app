<script>
    import {
        Container,
        Nav,
        Navbar,
        NavbarBrand,
        NavItem,
        NavLink
    } from 'sveltestrap';
    import {onMount} from "svelte";
    import {getContext} from "svelte";
    import {createEventDispatcher} from 'svelte';

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
            console.log(res);
            throw new Error(res);
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

    async function login(event) {
        // href="/oauth2/authorization/github"
        const response = await fetch(baseUrl + "/login", {
            method: 'GET'
        });
        const text = await response.text();
        if (response.ok) {
            authorized = true;
            dispatch('menu', '');
        } else {
            throw new Error(text);
        }

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
                    <NavLink on:click={logout} disabled='{inProgress}'>Logout</NavLink>
                </NavItem>
            {:else}
                <NavItem>
<!--                    <NavLink on:click={login} title="Login with GitHub">Sign in</NavLink>-->
                      <link href="/login" title="basic Login">Sign in</link>
                </NavItem>
            {/if}
        </Nav>
    </Container>
</Navbar>