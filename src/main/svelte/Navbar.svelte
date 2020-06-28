<script>
  import {
    Container,
    Nav,
    Navbar,
    NavbarBrand,
    NavItem,
    NavLink
  } from 'sveltestrap';

  import { createEventDispatcher } from 'svelte';
  const dispatch = createEventDispatcher();
</script>

<Navbar light expand="sm">
  <Container>
    <NavbarBrand href="/">readme app</NavbarBrand>
    <Nav class="ml-auto" navbar>
      <NavItem>
      <!-- overview -->
        <NavLink href="#" on:click={() => dispatch('menu', '')}>Home</NavLink>
      </NavItem>
      <NavItem>
        <!-- /editor -->
        <NavLink href="#" on:click={() => dispatch('menu', 'editor')}>New</NavLink>
      </NavItem>
      <NavItem>
        <!-- /login -->
        <NavLink href="/oauth2/authorization/github" title="Login with GitHub">Sign in</NavLink>
      </NavItem>
      <NavItem>
        <!-- /logout -->
        <NavLink href="/logout">Logout</NavLink>
      </NavItem>      
      <NavItem>
        <!-- /user -->
        <NavLink href="#" on:click={() => dispatch('menu', 'user')}>User</NavLink>
      </NavItem>
    </Nav>
  </Container>
</Navbar>