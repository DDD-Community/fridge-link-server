package mara.server.exception

import mara.server.common.ErrorResponse
import mara.server.common.fail
import mara.server.util.logger
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {
    val log = logger()

    @ExceptionHandler(InvalidDeployStatusException::class, InvalidS3PathException::class, NoSuchElementException::class)
    fun handleBadRequestException(ex: Exception): ResponseEntity<ErrorResponse> {
        log.warn("Custom Exception [{}] was handled: {}", ex.javaClass.simpleName, ex.message)
        return fail(HttpStatus.BAD_REQUEST, ex.message ?: "BadRequestException occurred")
    }

    @ExceptionHandler(IllegalAccessShareException::class)
    fun handleForbiddenException(ex: Exception): ResponseEntity<ErrorResponse> {
        log.warn("Custom Exception [{}] was handled: {}", ex.javaClass.simpleName, ex.message)
        return fail(HttpStatus.FORBIDDEN, ex.message ?: "ForbiddenException occurred")
    }
}
