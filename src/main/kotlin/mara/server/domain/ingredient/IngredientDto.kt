package mara.server.domain.ingredient

data class IngredientRequest(
    val category: String,
    val name: String,
    val iconImage: String,
    val expirationDays: Int
)

data class IngredientResponse(
    val ingredientId: Long,
    val category: String,
    val name: String,
    val iconImage: String,
    val expirationDays: Int
) {
    constructor(ingredient: Ingredient) : this(
        ingredientId = ingredient.ingredientId,
        category = ingredient.category,
        name = ingredient.name,
        iconImage = ingredient.iconImage,
        expirationDays = ingredient.expirationDays
    )
}

fun List<Ingredient>.toIngredientResponseList(): List<IngredientResponse> {
    return this.map { IngredientResponse(it) }
}
