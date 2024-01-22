package mara.server.domain.ingredient

data class IngredientResponse(
    val category: String?,
    val name: String?,
    val iconImage: String?,
    val expirationDays: Int
) {
    constructor(ingredient: Ingredient) : this(
        category = ingredient.category,
        name = ingredient.name,
        iconImage = ingredient.iconImage,
        expirationDays = ingredient.expirationDays
    )
}