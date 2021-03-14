package cane.brothers;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * TODO version inc
 */
@Order(2)
@Profile("openapi")
@Configuration
public class OpenApiConfig extends WebSecurityConfigurerAdapter {

    private static final String[] AUTH_WHITELIST = {
            "/swagger/**",
            "/swagger-ui/**"
    };

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(AUTH_WHITELIST);
    }

    @Bean
    public OpenAPI readmeAppOpenAPI() {
        return new OpenAPI()
                .addTagsItem(new Tag()
                        .name("article-controller")
                        .description("article's operations"))
                .addTagsItem(new Tag()
                        .name("tag-controller")
                        .description("tag's operations"))
                .info(new Info()
                        .title("Readme app REST API")
                        .description("Spring Boot RESTful service using springdoc-openapi and OpenAPI 3.")
                        .termsOfService("readme app terms")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Readme App API Support")
                                .url("http://webcane.github.io/#contact")
                                .email("webcane@yandex.ru"))
                );
    }
}