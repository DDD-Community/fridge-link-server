package mara.server.common

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

data class CommonResponse<T>(
    var message: String = "ok",
    var data: T? = null,
)

fun <T> success(data: T? = null): CommonResponse<T> = CommonResponse(data = data)

fun<T> fail(message: String): ResponseEntity<CommonResponse<T>> = ResponseEntity
    .status(HttpStatus.BAD_REQUEST)
    .body(CommonResponse(message = message))
