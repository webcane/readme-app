package cane.brothers.sec;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.web.cors.CorsUtils;

/**
 * @author mniedre
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf(c -> c
                        .disable())
                .authorizeRequests(a -> a
                        .antMatchers("/", "/error").permitAll()
                        .antMatchers("/login").permitAll()
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
//        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "https://github.com/login/oauth/authorize"));
//        configuration.setAllowedMethods(Arrays.asList("GET"));
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
}
