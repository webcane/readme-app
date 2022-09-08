package cane.brothers;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

/**
 * @author mniedre
 */
@TestConfiguration
public class TestConfig {

    @MockBean
    private ClientRegistrationRepository clientRegistrationRepository;

    @Bean
    @Primary
    public UserDetailsService userDetailsService() {
        User basicUser = new org.springframework.security.core.userdetails.User(
            "testuser",
            "password",
            Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));

        User blaUser = new org.springframework.security.core.userdetails.User(
            "blauser",
            "password",
            Arrays.asList(new SimpleGrantedAuthority("ROLE_BLA")));

        return new InMemoryUserDetailsManager(basicUser, blaUser);
    }
}
