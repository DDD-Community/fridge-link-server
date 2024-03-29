package mara.server.domain.friend

import mara.server.domain.ingredient.Ingredient
import mara.server.domain.refrigerator.Refrigerator
import mara.server.domain.user.User

data class FriendRefrigeratorResponse(
    var userId: Long,
    val nickname: String,
    val refrigeratorId: Long,
    val friendRefrigeratorIngredientGroupList: List<FriendRefrigeratorIngredient>
) {
    constructor(user: User, refrigerator: Refrigerator, ingredientList: List<Ingredient>) : this(
        userId = user.userId,
        nickname = user.nickname,
        refrigeratorId = refrigerator.refrigeratorId,
        friendRefrigeratorIngredientGroupList = ingredientList.map { FriendRefrigeratorIngredient(it) }
    )
}

data class FriendRefrigeratorIngredient(
    val name: String,
    val iconImage: String,
) {
    constructor(ingredient: Ingredient) : this(
        name = ingredient.name,
        iconImage = ingredient.iconImage
    )
}
