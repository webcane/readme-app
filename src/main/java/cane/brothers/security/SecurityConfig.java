package cane.brothers.security;

import cane.brothers.security.oauth2.AppOAuth2UserService;
import cane.brothers.security.preauth.PreAuthSecurityConfigurer;
import cane.brothers.security.stateless.OAuth2AuthenticationFailureHandler;
import cane.brothers.security.stateless.OAuth2AuthenticationSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.CorsEndpointProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
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
public class SecurityConfig {


  public static final String DEFAULT_LOGIN_REDIRECT_URI = "/login/token";

  private final AppOAuth2UserService appOAuth2UserService;
  private final OAuth2AuthenticationSuccessHandler successHandler;
  private final OAuth2AuthenticationFailureHandler failureHandler;

  private final AuthorizationRequestRepository<OAuth2AuthorizationRequest> cookieAuthorizationRequestRepository;

  private final PreAuthSecurityConfigurer preAuthConfigurer;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.cors(Customizer.withDefaults());
    http.csrf(c -> c.disable());
    http.httpBasic(h -> h.disable());
    http.formLogin(f -> f.disable());

    // disable session creation on Spring Security
    http.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    // TODO redirect to frontend
    // store SecurityContext in Cookie / delete Cookie on logout
    //http.securityContext(sc -> sc.securityContextRepository(cookieSecurityContextRepository));
    //http.logout(l -> l.permitAll().deleteCookies(SignedUserInfoCookie.NAME));
    // deactivate RequestCache and append originally requested URL as query parameter to login form request
    //http.requestCache(rc -> rc.disable());

    // restrict access to endpoints
    http.authorizeHttpRequests(a ->
        a.requestMatchers("/", "/error").permitAll()
            .requestMatchers("/auth/*", "/oauth2/*", "/login", "/login*", "/login/*").permitAll()
            .requestMatchers("/public/*", "/favicon/*", "/build/*", "/fonts/*").permitAll()
            .requestMatchers("/api/tags", "/api/articles", "/api/user").hasAnyRole("USER")
            .anyRequest().authenticated()
    );

//    http.exceptionHandling(e -> e.authenticationEntryPoint((request, response, authException) ->
//        response.sendError(HttpServletResponse.SC_UNAUTHORIZED)));
//    http.exceptionHandling(e -> e.defaultAuthenticationEntryPointFor(new Http403ForbiddenEntryPoint(),
//        AnyRequestMatcher.INSTANCE));

    // throws Access Denied exception only once
    http.exceptionHandling(e -> e.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.FORBIDDEN)));

    // preAuth token direct usage
    http.apply(preAuthConfigurer);

    // oauth2 login and preAuth token generation
    http.oauth2Login(o -> o.defaultSuccessUrl(DEFAULT_LOGIN_REDIRECT_URI)
        /**
         By default, Spring OAuth2 uses HttpSessionOAuth2AuthorizationRequestRepository to save
         the authorization request. But, since our service is stateless, we can't save it in
         the session. We'll save the request in a Base64 encoded cookie instead.
         */
        .authorizationEndpoint(a -> a.authorizationRequestRepository(cookieAuthorizationRequestRepository))
        .userInfoEndpoint(u -> u.userService(appOAuth2UserService))
        // remove cookie, generate access token and redirect
        .successHandler(successHandler)
        // remove cookie and redirect
        .failureHandler(failureHandler));

    return http.build();
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource(CorsEndpointProperties corsProperties) {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", corsProperties.toCorsConfiguration());
    return source;
  }

  @Bean
  public WebSecurityCustomizer webSecurity() {
    return web -> web.ignoring()
        // swagger
        .requestMatchers("/webjars/**", "/v3/api-docs/**", "/swagger", "/swagger/swagger-config",
            "/swagger-ui", "/swagger-ui/**")
        // jwt
        .requestMatchers("/jwt", "/jwt/**", "/token/**")
        // login
        .requestMatchers("/login", DEFAULT_LOGIN_REDIRECT_URI + "*")
        // resources
        .requestMatchers("/favicon.ico", "/*/*.png", "/*/*.gif", "/*/*.svg", "/*/*.jpg", "/*/*.html",
            "/*/*.css", "/*/*.js")
        // actuator
        .requestMatchers("/management", "/management/**", "/actuator/**");
  }
}
