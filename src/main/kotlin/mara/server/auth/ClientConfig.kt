package mara.server.auth

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class ClientConfig {
    @Bean
    fun restTemplate(): RestTemplate {
        return RestTemplate()
    }
}

enum class DeployStatus(val uri:String) {
    LOCAL(""),
    DEV("http://localhost:3000/login"),
    PROD("https://fridgelink.site/login")
}
