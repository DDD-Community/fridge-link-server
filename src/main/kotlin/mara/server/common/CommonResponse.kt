package mara.server.common

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

data class CommonResponse<T>(
    var message: String = "ok",
    var data: T? = null,
)

data class ErrorResponse(
    var message: String
)

fun <T> success(data: T? = null): CommonResponse<T> = CommonResponse(data = data)

fun fail(httpStatus: HttpStatus, message: String): ResponseEntity<ErrorResponse> = ResponseEntity
    .status(httpStatus)
    .body(ErrorResponse(message = message))
