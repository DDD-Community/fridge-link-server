package mara.server.domain

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthController {

    @GetMapping("/")
    fun healthCheck(): HttpStatus {
        return HttpStatus.OK
    }
}
