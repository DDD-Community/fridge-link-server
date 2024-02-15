package mara.server.config.swagger

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.security.SecurityScheme
import io.swagger.v3.oas.annotations.security.SecuritySchemes
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@OpenAPIDefinition
@SecuritySchemes(
    SecurityScheme(
        name = "jwtAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        description = "JWT (JSON Web Token) 인증을 위한 헤더"
    ),
    SecurityScheme(
        name = "Refresh-Token",
        type = SecuritySchemeType.APIKEY,
        `in` = SecuritySchemeIn.HEADER,
        description = "Refresh Token을 포함한 헤더"
    )
)
class SwaggerConfig {
    @Bean
    fun openAPI(): OpenAPI {
        return OpenAPI()
            .addSecurityItem(SecurityRequirement().addList("jwtAuth"))
            .addSecurityItem(SecurityRequirement().addList("Refresh-Token"))
            .info(apiInfo())
    }

    private fun apiInfo() = Info()
        .title("Mara Server Api")
        .description("마라 API swagger doc")
        .version("1.0.0")
}
