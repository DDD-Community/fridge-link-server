package mara.server.domain.ingredient

import org.springframework.data.domain.Page
import java.time.LocalDateTime

data class IngredientDetailRequest(
    var refrigeratorId: Long,
    var ingredientId: Long,
    val name: String,
    val quantity: Int,
    val location: IngredientLocation,
    val memo: String?,
    val addDate: LocalDateTime,
    val expirationDate: LocalDateTime,
    val isDeleted: Boolean = false
)

data class IngredientDetailUpdateRequest(
    val name: String,
    val quantity: Int,
    val location: IngredientLocation,
    val memo: String?,
    val addDate: LocalDateTime,
    val expirationDate: LocalDateTime,
    val isDeleted: Boolean = false
)

data class IngredientDetailResponse(
    val ingredientDetailId: Long,
    val ingredientImage: String,
    val name: String,
    val quantity: Int,
    val location: IngredientLocation,
    val memo: String?,
    val addDate: LocalDateTime,
    val expirationDate: LocalDateTime,
    val isDeleted: Boolean
) {

    constructor(ingredientDetail: IngredientDetail) : this(
        ingredientDetailId = ingredientDetail.ingredientDetailId,
        ingredientImage = ingredientDetail.ingredient.iconImage,
        name = ingredientDetail.name,
        quantity = ingredientDetail.quantity,
        location = ingredientDetail.location,
        memo = ingredientDetail.memo,
        addDate = ingredientDetail.addDate,
        expirationDate = ingredientDetail.expirationDate,
        isDeleted = ingredientDetail.isDeleted
    )
}

fun Page<IngredientDetail>.toIngredientDetailResponseListPage(): Page<IngredientDetailResponse> {
    return this.map { IngredientDetailResponse(it) }
}

fun List<IngredientDetail>.toIngredientDetailResponseList(): List<IngredientDetailResponse> {
    return this.map { IngredientDetailResponse(it) }
}
