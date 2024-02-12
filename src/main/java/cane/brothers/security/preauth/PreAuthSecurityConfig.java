package cane.brothers.security.preauth;

import cane.brothers.security.oauth2.AppOAuth2Authorities;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedGrantedAuthoritiesUserDetailsService;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedGrantedAuthoritiesWebAuthenticationDetails;

@Configuration
@RequiredArgsConstructor
public class PreAuthSecurityConfig {

    @Bean
    protected AuthenticationDetailsSource<HttpServletRequest,
            PreAuthenticatedGrantedAuthoritiesWebAuthenticationDetails> authDetailsSource() {
        return (request) -> new PreAuthenticatedGrantedAuthoritiesWebAuthenticationDetails(request,
                AppOAuth2Authorities.DEFAULT_AUTHORITIES);
    }

    @Bean
    protected PreAuthenticatedAuthenticationProvider preAuthProvider(
            PreAuthenticatedGrantedAuthoritiesUserDetailsService preAuthUserDetailsService) {
        var provider = new PreAuthenticatedAuthenticationProvider();
        provider.setPreAuthenticatedUserDetailsService(preAuthUserDetailsService);
        return provider;
    }

    @Bean
    protected PreAuthenticatedGrantedAuthoritiesUserDetailsService preAuthUserDetailsService() {
        return new PreAuthenticatedGrantedAuthoritiesUserDetailsService();
    }


}
