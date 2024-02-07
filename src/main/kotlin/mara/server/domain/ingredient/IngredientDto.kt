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

data class IngredientGroupResponse(
    val category: String,
    val ingredientGroupList: List<IngredientGroup>
) {
    constructor(ingredientList: List<Ingredient>) : this(
        category = ingredientList.firstOrNull()?.category ?: "",
        ingredientGroupList = ingredientList.map { IngredientGroup(it) }
    )
}

data class IngredientGroup(
    val id: Long,
    val name: String,
    val iconImage: String
) {
    constructor(ingredient: Ingredient) : this(
        id = ingredient.ingredientId,
        name = ingredient.name,
        iconImage = ingredient.iconImage
    )
}

fun List<Ingredient>.toIngredientResponseList(): List<IngredientResponse> {
    return this.map { IngredientResponse(it) }
}

fun List<Ingredient>.toIngredientCategoryGroupResponseList(): List<IngredientGroupResponse> {
    return this.groupBy { it.category }.values.map { IngredientGroupResponse(it) }
}
