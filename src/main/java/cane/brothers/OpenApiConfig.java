package cane.brothers;

import static cane.brothers.OpenApiConfig.AUTH_SCHEME;

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

@Order(2)
@Profile({"openapi","info"})
@Configuration
@OpenAPIDefinition(security = {@SecurityRequirement(name = AUTH_SCHEME)})
@SecurityScheme(name = AUTH_SCHEME, type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "Bearer")
public class OpenApiConfig {

  public static final String AUTH_SCHEME = "bearerAuth";

  @Bean
  public OpenAPI readmeAppOpenAPI(@Value("${info.app.name}") String appName,
                                  @Value("${info.app.version}") String appVersion,
                                  @Value("${info.app.description}") String appDescription,
                                  @Value("${info.app.email}") String appEmail,
                                  @Value("${info.app.url}") String appUrl) {
    return new OpenAPI()
        .info(new Info()
            .title(appName)
            .description(appDescription)
            // .termsOfService("readme app terms")
            .version(appVersion)
            .contact(new Contact()
                .url(appUrl)
                .email(appEmail)));
  }
}