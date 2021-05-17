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
}
