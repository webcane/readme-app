package cane.brothers;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

/**
 * @author mniedre
 */
@Slf4j
@SpringBootTest(classes = {Environment.class})
public class ProfileTest {

    @Autowired
    private Environment env;

    @Test
    public void test_activeProfiles() {
        for (String profileName : env.getActiveProfiles()) {
            log.info("Currently active profile - {}", profileName);
        }
        Assertions.assertThat(env.getActiveProfiles())
                .hasSize(2)
                .contains("test", "oauth2");
    }
}
