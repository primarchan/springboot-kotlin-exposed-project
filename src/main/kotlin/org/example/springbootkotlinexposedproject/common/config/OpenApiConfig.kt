package org.example.springbootkotlinexposedproject.common.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.License
import io.swagger.v3.oas.models.servers.Server
import org.springframework.context.annotation.Bean
import io.swagger.v3.oas.models.ExternalDocumentation
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {

    @Bean
    fun customOpenAPI(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("SPRINGBOOT-KOTLIN-EXPOSED-PROJECT")
                    .description("API Documentation for SPRINGBOOT-KOTLIN-EXPOSED-PROJECT")
                    .version("1.0.0")
                    .contact(
                        Contact()
                            .name("Support Team")
                            .email("support@example.com")
                            .url("https://example.com")
                    )
                    .license(
                        License()
                            .name("Apache 2.0")
                            .url("https://www.apache.org/licenses/LICENSE-2.0")
                    )
            )
            .externalDocs(
                ExternalDocumentation()
                    .description("Project GitHub Repository")
                    .url("https://github.com/primarchan/springboot-kotlin-exposed-project")
            )
            .servers(
                listOf(
                    Server().url("http://localhost:8080").description("Local environment"),
                    Server().url("https://api.example.com").description("Production environment")
                )
            )
    }
}