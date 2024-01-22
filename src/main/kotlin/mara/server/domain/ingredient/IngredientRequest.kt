package mara.server.domain.ingredient

data class IngredientRequest(
    val category: String,
    val name: String,
    val iconImage: String,
    val expirationDays: Int
)
