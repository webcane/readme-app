package cane.brothers.sec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by cane
 */
@Profile("dev")
@Order(10)
@EnableWebSecurity
public class LocalSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("user")
                .password(passwordEncoder().encode("user")).roles("USER");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http
                .csrf()
                .disable()
                .httpBasic()
                .and()
                // TODO
//                .authorizeRequests()
//                    .antMatchers("/tags", "/articles", "/user").hasRole("USER")
//                    .antMatchers("/", "/error", "/public/**", "/favicon/**",
//                            "/build/**", "/fonts/**", "/management/**", "/actuator/**").permitAll()
//                    .anyRequest().authenticated()
//                    .and()
                .formLogin()
                .loginProcessingUrl("/oauth2/authorization/github")
                .permitAll()
                .and()
                .logout()
                .deleteCookies("JSESSIONID");
    }
}
