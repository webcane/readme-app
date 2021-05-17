package cane.brothers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author mniedre
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = TestConfig.class)
public class AuthRestIT {

    @Autowired
    private TestRestTemplate restTemplate;

    // FIXME
//    @Test
//    public void homePage() throws Exception {
//        ResponseEntity<String> entity = this.restTemplate.getForEntity("/", String.class);
//        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
//    }

    @Test
    public void userPage() throws Exception {
        ResponseEntity<String> entity = this.restTemplate.getForEntity("/user", String.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }
}
