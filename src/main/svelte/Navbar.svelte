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
        <NavLink href="#" on:click={() => dispatch('menu', 'login')}>Sign in</NavLink>
      </NavItem>
      <NavItem>
        <!-- /register -->
        <NavLink href="#" on:click={() => dispatch('menu', 'register')}>Sign up</NavLink>
      </NavItem>
    </Nav>
  </Container>
</Navbar>