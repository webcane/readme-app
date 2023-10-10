package cane.brothers.security;

import cane.brothers.security.oauth2.CustomOAuth2UserService;
import cane.brothers.security.preauth.PreAuthSecurityConfigurer;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
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

  // TODO
  private final CustomUserDetailsService customUserDetailsService;

  private final CustomOAuth2UserService customOAuth2UserService;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.cors(Customizer.withDefaults());
    http.csrf(c -> c.disable());
    http.httpBasic(h -> h.disable());
    http.formLogin(f -> f.disable());
    // disable session creation on Spring Security
    http.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
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
    // 1. oauth2 login and preAuth token generation
    http.oauth2Login(o -> o.userInfoEndpoint(u -> u.userService(customOAuth2UserService)));
    // 2. preAuth token direct usage
    http.apply(preAuthSecurityConfigurer());
    return http.build();
  }

  private AbstractHttpConfigurer<PreAuthSecurityConfigurer, HttpSecurity> preAuthSecurityConfigurer() {
    return new PreAuthSecurityConfigurer();
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
