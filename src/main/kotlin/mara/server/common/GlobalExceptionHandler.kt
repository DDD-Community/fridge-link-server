package mara.server.common

import mara.server.util.logger
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {
    val log = logger()
    @ExceptionHandler(InvalidDeployStatusException::class)
    fun handleInvalidDeployStatusException(ex: InvalidDeployStatusException): ResponseEntity<CommonResponse<Any>> {
        log.warn("[{}] handled: {}", ex.javaClass.simpleName, ex.message)
        val httpStatus = HttpStatus.BAD_REQUEST
        val data = ex.additionalData
        return fail(ex.message ?: "An error occurred", httpStatus, data)
    }
}