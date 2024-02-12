package cane.brothers.swagger;


import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;

import java.util.List;

/**
 * TODO version inc
 */
@Order(2)
@Profile("openapi")
@Configuration
@SecuritySchemes(value = {
        @SecurityScheme(name = OpenApiConfig.AUTH_HEADER, type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER,
                description = "{access_token}", scheme = "Bearer", bearerFormat = "JWT"),
//    @SecurityScheme(name = "security_auth", type = SecuritySchemeType.OAUTH2, flows =
//    @OAuthFlows(authorizationCode = @OAuthFlow(authorizationUrl = "${springdoc.oAuthFlow.aouthorizationUrl}",
//        tokenUrl = "@{springdoc.oAuthFlow.tokenUrl}",
//        scopes = {@OAuthScope(name = "openid", description = "openid scope")})
//    ))
})
public class OpenApiConfig {

    public static final String AUTH_HEADER = "Authorization";

    private static final String[] AUTH_WHITELIST = {
            "/webjars/*",
            "/v3/api/docs/*",
            "/swagger",
            "/swagger-ui",
            "/swagger/*",
            "/swagger-ui/*"
    };

    /**
     * exclude from spring security chain the url's specific to swagger ui.
     *
     * @return WebSecurityCustomizer configured web security
     */
    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring().requestMatchers(AUTH_WHITELIST);
    }

    @Bean
    public OpenAPI readmeAppOpenAPI() {
        var secReq = new SecurityRequirement();
        secReq.addList(AUTH_HEADER);

        return new OpenAPI()
                .addTagsItem(new Tag()
                        .name("article api")
                        .description("article's operations"))
                .addTagsItem(new Tag()
                        .name("tag api")
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
                )
                .security(List.of(secReq));
    }
}