package cane.brothers.security.preauth;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedGrantedAuthoritiesWebAuthenticationDetails;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;
import org.springframework.security.web.header.HeaderWriterFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@RequiredArgsConstructor
public class PreAuthSecurityConfigurer extends AbstractHttpConfigurer<PreAuthSecurityConfigurer, HttpSecurity> {

  private final PreAuthenticatedAuthenticationProvider authProvider;
  private final AuthenticationDetailsSource<HttpServletRequest,
      PreAuthenticatedGrantedAuthoritiesWebAuthenticationDetails> authDetailsSource;

  @Override
  public void configure(HttpSecurity http) {
    AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
    builder.authenticationProvider(authProvider);
    AuthenticationManager authenticationManager = builder.getOrBuild();

    // add preAuth token based authentication filter
    http.addFilterAfter(preAuthenticationFilter(authenticationManager), HeaderWriterFilter.class);
  }

  protected RequestHeaderAuthenticationFilter preAuthenticationFilter(AuthenticationManager authManager) {
    var filter = new RequestHeaderAuthenticationFilter();
    // Authorization holds Bearer preAuth token
    filter.setPrincipalRequestHeader("Authorization");
    // do not throw exception when header is not present
    filter.setExceptionIfHeaderMissing(false);
    filter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/api/**"));
    filter.setAuthenticationManager(authManager);
    filter.setAuthenticationDetailsSource(authDetailsSource);
    // TODO
    // filter.setAuthenticationSuccessHandler();
    return filter;
  }
}
