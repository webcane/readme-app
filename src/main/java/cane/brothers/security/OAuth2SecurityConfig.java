package cane.brothers.security;

import cane.brothers.security.oauth2.CustomOAuth2UserService;
import cane.brothers.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import cane.brothers.security.oauth2.OAuth2AuthenticationFailureHandler;
import cane.brothers.security.oauth2.OAuth2AuthenticationSuccessHandler;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedGrantedAuthoritiesUserDetailsService;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedGrantedAuthoritiesWebAuthenticationDetails;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;
import org.springframework.security.web.header.HeaderWriterFilter;
import org.springframework.security.web.util.matcher.AnyRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * @author mniedre
 */
@Order(1)
@Profile("oauth2")
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class OAuth2SecurityConfig {

  private final CustomUserDetailsService customUserDetailsService;

  private final CustomOAuth2UserService customOAuth2UserService;

  private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

  private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;


  /*
    By default, Spring OAuth2 uses HttpSessionOAuth2AuthorizationRequestRepository to save
    the authorization request. But, since our service is stateless, we can't save it in
    the session. We'll save the request in a Base64 encoded cookie instead.
  */
  private final HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }


  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.cors(Customizer.withDefaults());
    http.csrf(c -> c.disable());
    http.httpBasic(h -> h.disable());
    http.formLogin(f -> f.disable());
    // this disables session creation on Spring Security
    http.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    // Add our custom Token based authentication filter
    http.addFilterAfter(requestHeaderAuthenticationFilter(), HeaderWriterFilter.class);
    http.authorizeHttpRequests(a ->
        a.requestMatchers("/", "/error", "/favicon.ico",
                "/*/*.png", "/*/*.gif", "/*/*.svg", "/*/*.jpg",
                "/*/*.html", "/*/*.css", "/*/*.js").permitAll()
            .requestMatchers("/auth/*", "/oauth2/*", "/login").permitAll()
            .requestMatchers("/public/*", "/favicon/*", "/build/*", "/fonts/*").permitAll()
            .requestMatchers("/management/*", "/actuator/*").permitAll()
            .requestMatchers("/tags", "/articles", "/user").hasAnyRole("USER")
            .anyRequest().authenticated()
    );
//    http.exceptionHandling(e -> e.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)));
//    http.exceptionHandling(e -> e.authenticationEntryPoint((request, response, authException) ->
//        response.sendError(HttpServletResponse.SC_UNAUTHORIZED)));
    http.exceptionHandling(e -> e.defaultAuthenticationEntryPointFor(new Http403ForbiddenEntryPoint(),
        AnyRequestMatcher.INSTANCE));
    http.oauth2Login(o -> o
        .authorizationEndpoint(a -> a.authorizationRequestRepository(cookieAuthorizationRequestRepository))
        .userInfoEndpoint(u -> u.userService(customOAuth2UserService))
        // default SavedRequestAwareAuthenticationSuccessHandler
        .successHandler(oAuth2AuthenticationSuccessHandler)
        // default SimpleUrlAuthenticationFailureHandler
        .failureHandler(oAuth2AuthenticationFailureHandler)
    );
    return http.build();
  }

  //  @Bean
  public RequestHeaderAuthenticationFilter requestHeaderAuthenticationFilter() {
    RequestHeaderAuthenticationFilter filter = new RequestHeaderAuthenticationFilter();
    //filter.setPrincipalRequestHeader("X-Forwarded-User");
    filter.setPrincipalRequestHeader("Authorization");
    //filter.setCredentialsRequestHeader("X-Forwarded-Access-Token");
    //filter.setCredentialsRequestHeader("Authorization");
    // do not throw exception when header is not present.
    // one use case is for actuator endpoints and static assets where security headers are not required.
    filter.setExceptionIfHeaderMissing(false);

    //filter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/api/**"));
    filter.setAuthenticationManager(authenticationManager());
    filter.setAuthenticationDetailsSource(
        (AuthenticationDetailsSource<HttpServletRequest, PreAuthenticatedGrantedAuthoritiesWebAuthenticationDetails>)
            (request) -> new PreAuthenticatedGrantedAuthoritiesWebAuthenticationDetails(request,
                AuthorityUtils.createAuthorityList("ROLE_USER")
            )
    );
    filter.setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler());
    return filter;
  }

  //  @Bean
  protected AuthenticationManager authenticationManager() {
    return new ProviderManager(List.of(authenticationProvider()));
  }

  protected PreAuthenticatedAuthenticationProvider authenticationProvider() {
    var provider = new PreAuthenticatedAuthenticationProvider();
    provider.setPreAuthenticatedUserDetailsService(userDetailsService());
    return provider;
  }

  protected PreAuthenticatedGrantedAuthoritiesUserDetailsService userDetailsService() {
    var userDetailsServer = new PreAuthenticatedGrantedAuthoritiesUserDetailsService();
    return userDetailsServer;
  }


  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    // configuration.applyPermitDefaultValues();
    //configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200",));
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS", "DELETE", "PUT"));
    configuration.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Authorization"));
    // configuration.setMaxAge();
    // this line is important it sends only specified domain instead of *
    configuration.setAllowCredentials(true);
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
}
