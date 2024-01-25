package mara.server.config.swagger

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {
    @Bean
    fun openAPI(): OpenAPI {
        val jwtSchemeName = "jwtAuth"
        // API 요청 헤더에 인증 정보 포함
        val securityRequirement = SecurityRequirement().addList(jwtSchemeName)
        // SecuritySchemes 등록
        val components = Components()
            .addSecuritySchemes(
                jwtSchemeName,
                SecurityScheme()
                    .name(jwtSchemeName)
                    .type(SecurityScheme.Type.HTTP) // HTTP 방식
                    .scheme("bearer")
                    .bearerFormat("JWT")
            )

        return OpenAPI()
            .components(Components())
            .addSecurityItem(securityRequirement)
            .components(components)
            .info(apiInfo())
    }

    private fun apiInfo() = Info()
        .title("Mara Server Api")
        .description("마라 API swagger doc")
        .version("1.0.0")
}
