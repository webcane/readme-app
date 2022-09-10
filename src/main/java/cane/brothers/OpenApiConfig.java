package cane.brothers;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;

/**
 * TODO version inc
 */
@Order(2)
@Profile("openapi")
@Configuration
@OpenAPIDefinition(info = @Info(title = "readme", description = "Readme app springdoc open api",
    contact = @Contact(name = "support", email = "webcane@yandex.ru")),
    security = {@SecurityRequirement(name = OpenApiConfig.AUTH_SCHEME)})
@SecurityScheme(name = OpenApiConfig.AUTH_SCHEME, type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "Bearer")
public class OpenApiConfig {

  public static final String AUTH_SCHEME = "bearerAuth";

  /**
   * Constructor
   */
  public OpenApiConfig() {}
}