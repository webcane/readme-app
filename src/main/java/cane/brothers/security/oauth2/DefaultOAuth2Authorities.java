package cane.brothers.security.oauth2;

import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.GrantedAuthoritiesContainer;

public class DefaultOAuth2Authorities implements GrantedAuthoritiesContainer {

  public static final List<GrantedAuthority> DEFAULT_AUTHORITIES = AuthorityUtils.createAuthorityList("ROLE_USER");

  @Override
  public Collection<? extends GrantedAuthority> getGrantedAuthorities() {
    return DEFAULT_AUTHORITIES;
  }
}
