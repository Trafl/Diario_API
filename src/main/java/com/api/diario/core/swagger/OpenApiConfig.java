package com.api.diario.core.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Diario Escolar API")
                        .version("v1")
                        .description("Documento para auxiliar no manuseio da API"))
                .components(new Components()
                        .addSchemas("ProblemDetail", new Schema()
                                .type("object")
                                .addProperty("type", new StringSchema().example("https://api/v1/aluno/entity-not-found"))
                                .addProperty("title", new StringSchema().example("Aluno não encontrado"))
                                .addProperty("status", new StringSchema().example(404))
                                .addProperty("detail", new StringSchema().example("Aluno de Id xx não foi encontrado"))
                                .addProperty("timestamp", new StringSchema().example("2024-08-16sT19:32:54.253417400Z"))
                        ));
    }
}
