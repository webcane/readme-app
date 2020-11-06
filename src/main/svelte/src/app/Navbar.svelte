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
        console.log('check User');
        const opts = { method: 'GET', headers: {} };
        // opts.headers['Authorization'] = `Basic dXNlcjp1c2Vy`;
        const res = await fetch(baseUrl + "/user", opts);

        if (res.ok && !res.redirected) {
            authorized = true;
            const json = await res.json();
            console.log('User: ' + json.name);
        } else {
            authorized = false;
            console.log(res);
        }
    }

    async function logout(event) {
        inProgress = true;
        const opts = { method: 'POST', headers: {} };
        // opts.headers['Authorization'] = `Basic dXNlcjp1c2Vy`;
        const response = await fetch(baseUrl + "/logout", opts);
        const text = await response.text();
        if (response.ok) {
            authorized = false;
            dispatch('nav', 'logout');
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
                    <NavLink href="#" on:click={() => dispatch('nav', 'editor')}>New</NavLink>
                </NavItem>
                <NavItem>
                    <!-- /logout -->
                    <NavLink href="#" on:click={logout} disabled='{inProgress}'>Logout</NavLink>
                </NavItem>
            {:else}
                <NavItem>
                      <NavLink href="/login" title="basic Login">Sign in</NavLink>
                </NavItem>
            {/if}
        </Nav>
    </Container>
</Navbar>