package mara.server.domain.refrigerator

data class RefrigeratorRequest(
    var name: String,
    val userId: Long
)
