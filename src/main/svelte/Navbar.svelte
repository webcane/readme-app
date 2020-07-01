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
    import {createEventDispatcher} from 'svelte';

    const dispatch = createEventDispatcher();

    import {getContext} from "svelte";

    let baseUrl = getContext("baseUrl");

    let user = 'User';
    let userPromise;
    onMount(checkUser);

    async function checkUser() {
        console.log('check User:');
        const res = await fetch(baseUrl + "/user");
        const json = await res.json();

        if (res.ok) {
            authorized = true;
            user = json;
        } else {
            authorized = false;
            log.console(json);
            throw new Error(json);
        }
    }

    let inProgress = false;

    async function logout(event) {
        inProgress = true;
        const response = await fetch(baseUrl + "/logout", {
            method: 'POST'
        });
        const text = await response.text();
        if (response.ok) {
            authorized = false;
            user = 'User';
            dispatch('menu', 'logout');
        } else {
            throw new Error(text);
        }
        inProgress = false;
    }

    //="{user === 'User'}"
    let authorized = false;
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
                <NavItem>
                    <!-- /user -->
                    <NavLink href="#" on:click={() => dispatch('menu', 'user')}>{user.name}</NavLink>
                </NavItem>
            {:else}
                <NavItem>
                    <NavLink href="/oauth2/authorization/github" title="Login with GitHub">Sign in</NavLink>
                </NavItem>
            {/if}
        </Nav>
    </Container>
</Navbar>