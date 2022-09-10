package cane.brothers;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;

/**
 * TODO version inc
 */
@Order(2)
@Profile("openapi")
@Configuration
@OpenAPIDefinition(security = {@SecurityRequirement(name = OpenApiConfig.AUTH_SCHEME)})
@SecurityScheme(name = OpenApiConfig.AUTH_SCHEME, type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme =
    "Bearer")
public class OpenApiConfig {

  public static final String AUTH_SCHEME = "bearerAuth";

  /**
   * Constructor
   */
  public OpenApiConfig() {
  }

  @Bean
  public OpenAPI readmeAppOpenAPI(@Value("${app.name}") String appName,
                                  @Value("${app.version}") String appVersion,
                                  @Value("${app.description}") String appDescription,
                                  @Value("${app.email}") String appEmail,
                                  @Value("${app.url}") String appUrl) {
    return new OpenAPI()
        .info(new Info()
            .title(appName)
            .description(appDescription)
            // .termsOfService("readme app terms")
            .version(appVersion)
            .contact(new Contact()
//                .name("API Support")
                .url(appUrl)
                .email(appEmail)));
  }
}