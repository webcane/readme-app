package cane.brothers.sec;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

/**
 * @author mniedre
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(a -> a.antMatchers("/", "/error").permitAll()
                        .antMatchers("/login").permitAll()
                        .antMatchers("/public/**", "/favicon/**", "/build/**", "/fonts/**").permitAll()
                        .antMatchers("/management/**", "/actuator/**").permitAll()
                        .antMatchers("/tags", "/articles", "/user").hasAnyRole("USER")
                        .anyRequest().authenticated())
                .exceptionHandling(e -> e.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .oauth2Login(o -> o.defaultSuccessUrl("/", true)
                        .failureUrl("/error"));
        //.logout(l -> l.logoutSuccessUrl("/logout"));
        //.and().csrf().disable();
    }
}
