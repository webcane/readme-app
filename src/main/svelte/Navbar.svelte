<script>
  import {
    Container,
    Nav,
    Navbar,
    NavbarBrand,
    NavItem,
    NavLink
  } from 'sveltestrap';
</script>

<Navbar light expand="sm">
  <Container>
    <NavbarBrand href="/">readme app</NavbarBrand>
    <Nav class="ml-auto" navbar>
      <NavItem>
        <NavLink href="/">Home</NavLink>
      </NavItem>
      <NavItem>
        <NavLink href="/login">Sign in</NavLink>
      </NavItem>
      <NavItem>
        <NavLink href="/register">Sign up</NavLink>
      </NavItem>
    </Nav>
  </Container>
</Navbar>