package mara.server.domain.refrigerator

data class RefrigeratorResponse(
    var id: Long,
    var name: String,
) {
    constructor(refrigerator: Refrigerator) : this(
        id = refrigerator.refrigeratorId,
        name = refrigerator.name
    )
}