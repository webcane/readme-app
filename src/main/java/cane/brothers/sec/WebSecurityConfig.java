package cane.brothers.sec;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

/**
 * @author mniedre
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
//                .sessionManagement()
//                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                    .and()
                .csrf(c -> c
                        .disable())
                .authorizeRequests(a -> a
                        .antMatchers("/", "/error").permitAll()
                        .antMatchers("/login", "/oauth2/**").permitAll()
                        .antMatchers("/public/**", "/favicon/**", "/build/**", "/fonts/**").permitAll()
                        .antMatchers("/management/**", "/actuator/**").permitAll()
                        .antMatchers("/tags", "/articles", "/user").hasAnyRole("USER")
                        .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                        .anyRequest().authenticated())
                .exceptionHandling(e -> e
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .oauth2Login(o -> o
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/error"))
//                        .failureHandler((request, response, exception) -> {
//                            request.getSession().setAttribute("error.message", exception.getMessage());
//                            handler.onAuthenticationFailure(request, response, exception);
//                        })
                .logout(l -> l
                                //.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .logoutSuccessUrl("/")
//                        .invalidateHttpSession(true)        // set invalidation state when logout
                                .deleteCookies("JSESSIONID")
//                        .clearAuthentication(true)
                );

//                .cors();
    }

    //    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5000", "http://localhost:3000", "https://github.com/login/oauth/authorize"));
//        configuration.setAllowedMethods(Arrays.asList("GET"));
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5000", "https://github.com")
                .allowedMethods("*");
    }
}
