package cane.brothers.security;

import cane.brothers.AppProperties;
import cane.brothers.security.oauth2.CustomOAuth2UserService;
import cane.brothers.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import cane.brothers.security.oauth2.OAuth2AuthenticationFailureHandler;
import cane.brothers.security.oauth2.OAuth2AuthenticationSuccessHandler;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * @author mniedre
 */
@Order(1)
@Profile("oauth2")
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class OAuth2SecurityConfig {

  @Autowired
  private AppProperties appProperties;

  @Autowired
  private CustomUserDetailsService customUserDetailsService;

  @Autowired
  private CustomOAuth2UserService customOAuth2UserService;

  @Autowired
  private OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

  @Autowired
  private OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

  @Autowired
  private HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository;

  @Bean
  public TokenAuthenticationFilter tokenAuthenticationFilter() {
    return new TokenAuthenticationFilter(appProperties, customUserDetailsService);
  }

  /*
    By default, Spring OAuth2 uses HttpSessionOAuth2AuthorizationRequestRepository to save
    the authorization request. But, since our service is stateless, we can't save it in
    the session. We'll save the request in a Base64 encoded cookie instead.
  */
  @Bean
  public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
    return new HttpCookieOAuth2AuthorizationRequestRepository(appProperties);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }


  @Bean(BeanIds.AUTHENTICATION_MANAGER)
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
    return authConfig.getAuthenticationManager();
  }


  @Bean
  public SecurityFilterChain configure2(HttpSecurity http) throws Exception {
    http
        .cors().and()
        .csrf(c -> c
            .disable())
        .httpBasic(h -> h.disable())
        .formLogin(f -> f.disable())
        // this disables session creation on Spring Security
        .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeRequests(a -> a
            .antMatchers("/", "/error", "/favicon.ico",
                "/**/*.png", "/**/*.gif", "/**/*.svg", "/**/*.jpg",
                "/**/*.html", "/**/*.css", "/**/*.js").permitAll()
            .antMatchers("/auth/**", "/oauth2/**", "/login").permitAll()
            .antMatchers("/public/**", "/favicon/**", "/build/**", "/fonts/**").permitAll()
            .antMatchers("/management/**", "/actuator/**").permitAll()
            .antMatchers("/webjars/**", "/v3/api-docs/**", "/swagger/**", "/swagger-ui/**").permitAll()
            .antMatchers("/tags", "/articles", "/user").hasAnyRole("USER")
            .anyRequest().authenticated())
        .exceptionHandling(e -> e.authenticationEntryPoint(
                new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
//                                new RestAuthenticationEntryPoint()
        )
        .oauth2Login(o -> o
            .authorizationEndpoint(a -> a
                .authorizationRequestRepository(cookieAuthorizationRequestRepository()))
            .userInfoEndpoint(u -> u
                .userService(customOAuth2UserService))
            // default SavedRequestAwareAuthenticationSuccessHandler
            .successHandler(oAuth2AuthenticationSuccessHandler)
            // default SimpleUrlAuthenticationFailureHandler
            .failureHandler(oAuth2AuthenticationFailureHandler)
        );

    // Add our custom Token based authentication filter
    http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    // configuration.applyPermitDefaultValues();
    configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
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
