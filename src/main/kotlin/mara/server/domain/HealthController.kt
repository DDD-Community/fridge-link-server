package mara.server.domain

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Tag(name = "서버 상태", description = "서버 상태 API")
class HealthController {

    @GetMapping("/health-check")
    @Operation(summary = "서버 상태 조회 API")
    fun healthCheck(): HttpStatus {
        return HttpStatus.OK
    }
}
