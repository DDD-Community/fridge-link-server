package mara.server.domain.ingredient

import java.time.LocalDateTime

data class IngredientDetailRequest(
    val quantity: Int,
    val location: String,
    val memo: String,
    val addDate: LocalDateTime,
    val expirationDate: LocalDateTime,
    val isDeleted: Boolean? = null
)
