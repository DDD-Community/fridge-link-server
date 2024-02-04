package mara.server.domain.refrigerator

data class RefrigeratorRequest(
    var name: String
)

data class RefrigeratorResponse(
    var id: Long,
    var name: String,
) {
    constructor(refrigerator: Refrigerator) : this(
        id = refrigerator.refrigeratorId,
        name = refrigerator.name
    )
}

fun List<Refrigerator>.toRefrigeratorResponseList(): List<RefrigeratorResponse> {
    return this.map { RefrigeratorResponse(it) }
}
