package cane.brothers;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;

/**
 * TODO version inc
 */
@Order(2)
@Profile("openapi")
@Configuration
@OpenAPIDefinition(info = @Info(title = "readme", description = "Readme app springdoc open api",
    contact = @Contact(name = "support", email = "webcane@yandex.ru")),
    security = {@SecurityRequirement(name = OpenApiConfig.COOKIE_NAME)})
@SecurityScheme(name = OpenApiConfig.COOKIE_NAME, type = SecuritySchemeType.APIKEY, in = SecuritySchemeIn.COOKIE)
public class OpenApiConfig {

  public static final String COOKIE_NAME = "oauth2_auth_request";

  private static final String[] AUTH_WHITELIST = {
      "/swagger/**",
      "/swagger-ui/**"
  };

  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return (web) -> web.ignoring().antMatchers(AUTH_WHITELIST);
  }

//  @Bean
//  public OpenAPI readmeAppOpenAPI() {
//    return new OpenAPI()
//        .info(new Info()
//            .title("Readme app REST API")
//            .description()
//            // .termsOfService("readme app terms")
//            .version("1.0.0")
//            .contact(new Contact()
//                .name("Readme App API Support")
//                .url("https://webcane.github.io/#contact")
//                .email("webcane@yandex.ru")));
//  }
}