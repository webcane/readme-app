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
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
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
@RequiredArgsConstructor
public class SecurityConfig {


//    public static final String DEFAULT_LOGIN_REDIRECT_URI = "/login/token";

    private final AppOAuth2UserService appOAuth2UserService;
    private final OAuth2AuthenticationSuccessHandler successHandler;
    private final OAuth2AuthenticationFailureHandler failureHandler;

    /**
     * By default, Spring OAuth2 uses HttpSessionOAuth2AuthorizationRequestRepository to save
     * the authorization request. But, since our service is stateless, we can't save it in
     * the session. We'll save the request in a Base64 encoded cookie instead.
     */
    private final AuthorizationRequestRepository<OAuth2AuthorizationRequest> cookieAuthorizationRequestRepository;

    private final PreAuthSecurityConfigurer preAuthConfigurer;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(Customizer.withDefaults());
        http.csrf(AbstractHttpConfigurer::disable);
        http.httpBasic(AbstractHttpConfigurer::disable);
        http.formLogin(AbstractHttpConfigurer::disable);
        http.anonymous(AbstractHttpConfigurer::disable); //AnonymousAuthenticationFilter

        // disable session creation on Spring Security
        http.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        // TODO redirect to frontend
        // store SecurityContext in Cookie / delete Cookie on logout
        //http.securityContext(sc -> sc.securityContextRepository(cookieSecurityContextRepository));
        //http.logout(l -> l.permitAll().deleteCookies(SignedUserInfoCookie.NAME));
        // deactivate RequestCache and append originally requested URL as query parameter to login form request
        //http.requestCache(rc -> rc.disable());

        // restrict access to endpoints
        http.authorizeHttpRequests(a -> a.requestMatchers("/", "/error").permitAll()
                .requestMatchers("/jwt", "/jwt/**", "/token/**").permitAll()
                // login
                .requestMatchers("/login/*").permitAll() // http://localhost:8080/login/oauth2/code/github?code=foo&state=bar
                .requestMatchers("/auth/*", "/oauth2/*").permitAll()
                .requestMatchers("/public/*", "/favicon/*", "/build/*", "/fonts/*").permitAll()
                .requestMatchers("/api/tags", "/api/articles", "/api/user").hasAnyRole("USER")
                // actuator
                .requestMatchers("/management", "/management/**", "/actuator/**").permitAll()
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

        // Add our custom Token based authentication filter
        //http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        // oauth2 login and preAuth token generation
        http.oauth2Login(o -> o.authorizationEndpoint(a -> a
                        // .baseUri("/oauth2/authorize")
                        .authorizationRequestRepository(cookieAuthorizationRequestRepository))
              //  .redirectionEndpoint(c -> c.baseUri("/oauth2/redirect"))
                .userInfoEndpoint(u -> u.userService(appOAuth2UserService))
                // remove cookie, generate access token and redirect
                .successHandler(successHandler)
                // remove cookie and redirect
                .failureHandler(failureHandler));

        return http.build();
    }

//    @Bean
//    public TokenAuthenticationFilter tokenAuthenticationFilter() {
//        return new TokenAuthenticationFilter();
//    }


    @Bean
    CorsConfigurationSource corsConfigurationSource(CorsEndpointProperties corsProperties) {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsProperties.toCorsConfiguration());
        return source;
    }

    @Bean
    public WebSecurityCustomizer webSecurity() {
        return web -> web.ignoring()
                // resources
                .requestMatchers("/favicon.ico", "/*/*.png", "/*/*.gif", "/*/*.svg", "/*/*.jpg", "/*/*.html",
                        "/*/*.css", "/*/*.js");
    }
}
