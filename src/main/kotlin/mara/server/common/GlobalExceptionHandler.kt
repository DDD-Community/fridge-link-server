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
    fun handleBadRequestException(ex: InvalidDeployStatusException): ResponseEntity<CommonResponse<Any>> {
        log.warn("[{}] handled: {}", ex.javaClass.simpleName, ex.message)

        /**
         * 차후 확장성을 고려하여 하단의 주석 처럼 수정 할 수 있음
         *
         * val httpStatus = when (ex) {
         *         is InvalidDeployStatusException -> HttpStatus.BAD_REQUEST
         *         is AnotherCustomException -> HttpStatus.INTERNAL_SERVER_ERROR
         *         else -> HttpStatus.INTERNAL_SERVER_ERROR // 기본적으로 설정할 HTTP 상태 코드
         *     }
         * */

        val httpStatus = HttpStatus.BAD_REQUEST
        val data = ex.additionalData
        return fail(ex.message ?: "An error occurred", httpStatus, data)
    }
}
