package cane.brothers.security.preauth;

import cane.brothers.security.oauth2.DefaultOAuth2Authorities;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedGrantedAuthoritiesUserDetailsService;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedGrantedAuthoritiesWebAuthenticationDetails;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;
import org.springframework.security.web.header.HeaderWriterFilter;

@RequiredArgsConstructor
public class PreAuthSecurityConfigurer extends AbstractHttpConfigurer<PreAuthSecurityConfigurer, HttpSecurity> {

  @Override
  public void configure(HttpSecurity http) {
    AuthenticationManagerBuilder authenticationManagerBuilder =
        http.getSharedObject(AuthenticationManagerBuilder.class);
    var authProvider = preAuthenticationProvider(preAuthUserDetailsService());
    authenticationManagerBuilder.authenticationProvider(authProvider);
    AuthenticationManager authenticationManager = authenticationManagerBuilder.getOrBuild();

    // Add preAuth token based authentication filter
    http.addFilterAfter(preAuthenticationFilter(authenticationManager), HeaderWriterFilter.class);
  }

  protected PreAuthenticatedAuthenticationProvider preAuthenticationProvider(
      PreAuthenticatedGrantedAuthoritiesUserDetailsService preAuthUserDetailsService) {
    var provider = new PreAuthenticatedAuthenticationProvider();
    provider.setPreAuthenticatedUserDetailsService(preAuthUserDetailsService);
    return provider;
  }

  protected PreAuthenticatedGrantedAuthoritiesUserDetailsService preAuthUserDetailsService() {
    var userDetailsServer = new PreAuthenticatedGrantedAuthoritiesUserDetailsService();
    return userDetailsServer;
  }

  protected RequestHeaderAuthenticationFilter preAuthenticationFilter(AuthenticationManager authenticationManager) {
    var filter = new RequestHeaderAuthenticationFilter();
    // Authorization holds Bearer preAuth token
    filter.setPrincipalRequestHeader("Authorization");
    // do not throw exception when header is not present
    filter.setExceptionIfHeaderMissing(false);
    // TODO
    //filter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/api/**"));
    filter.setAuthenticationManager(authenticationManager);
    filter.setAuthenticationDetailsSource((AuthenticationDetailsSource<HttpServletRequest,
        PreAuthenticatedGrantedAuthoritiesWebAuthenticationDetails>) (request) ->
        new PreAuthenticatedGrantedAuthoritiesWebAuthenticationDetails(request,
            DefaultOAuth2Authorities.DEFAULT_AUTHORITIES
        ));
    return filter;
  }
}
