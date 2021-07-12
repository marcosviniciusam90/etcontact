package com.mvam.etcontact.config.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Optional;

@Configuration
public class SwaggerConfiguration {

    public static final String AUTH_SCHEME = "authScheme";

    @Autowired
    private Environment environment;

    private final String title;
    private final String description;
    private final String termsOfServiceUrl;
    private final String version;
    private final String apiUrl;
    private final Optional<String> server;

    public SwaggerConfiguration(
            @Value("${spring.application.swagger.title}")
                    String title,
            @Value("${spring.application.swagger.description}")
                    String description,
            @Value("${spring.application.swagger.terms-of-service-url}")
                    String termsOfServiceUrl,
            @Value("${spring.application.swagger.version}")
                    String version,
            @Value("${spring.application.swagger.api-url}")
                    String apiUrl,
            @Value("${springdoc.server:#{null}}")
                    Optional<String> server
    ) {
        this.title = title;
        this.description = description;
        this.termsOfServiceUrl = termsOfServiceUrl;
        this.version = version;
        this.apiUrl = apiUrl;
        this.server = server;
    }

    @Bean
    public OpenAPI getOpenAPI() {
        OpenAPI api = new OpenAPI()
                .components(new Components().addSecuritySchemes(AUTH_SCHEME, securityScheme()))
                .info(
                        new Info()
                                .title(title)
                                .description(description + " Para obter autorização para realizar as requisições leia as instruções contidas no link abaixo.")
                                .termsOfService(termsOfServiceUrl)
                                .version(version)
                );

        server.ifPresent(s -> api.addServersItem(
                new Server()
                        .url(s)
        ));
        return api;
    }

    private SecurityScheme securityScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.OAUTH2)
                .flows(new OAuthFlows()
                        .password(new OAuthFlow()
                                .authorizationUrl(apiUrl + "/oauth/authorize")
                                .tokenUrl(apiUrl + "/oauth/token")));

    }
}
