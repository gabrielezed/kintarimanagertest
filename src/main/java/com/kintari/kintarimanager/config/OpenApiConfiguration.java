package com.kintari.kintarimanager.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("KintariManager API")
                        .description("API REST per il software gestionale della pizzeria 'cumenti si tocca'")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("KintariManager Team")
                                .email("support@kintarimanager.com")
                                .url("https://github.com/kintari/kintarimanager"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .components(new Components()
                        .addSecuritySchemes("basicAuth", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("basic")
                                .description("Autenticazione HTTP Basic con username e password")))
                .addSecurityItem(new SecurityRequirement().addList("basicAuth"));
    }
}