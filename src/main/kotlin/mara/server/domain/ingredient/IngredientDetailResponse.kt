package mara.server.domain.ingredient

import java.time.LocalDateTime

data class IngredientDetailResponse(
    val ingredientDetailId: Long,
    val quantity: Int,
    val location: String,
    val memo: String,
    val addDate: LocalDateTime,
    val expirationDate: LocalDateTime,
    val isDeleted: Boolean? = null
) {

    constructor(ingredientDetail: IngredientDetail) : this(
        ingredientDetailId = ingredientDetail.ingredientDetailId,
        quantity = ingredientDetail.quantity,
        location = ingredientDetail.location,
        memo = ingredientDetail.memo,
        addDate = ingredientDetail.addDate,
        expirationDate = ingredientDetail.expirationDate,
        isDeleted = ingredientDetail.isDeleted
    )
}