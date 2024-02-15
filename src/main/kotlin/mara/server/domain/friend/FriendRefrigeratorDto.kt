package mara.server.domain.friend

import mara.server.domain.ingredient.Ingredient
import mara.server.domain.ingredient.IngredientDetail
import mara.server.domain.ingredient.IngredientDetailResponse
import mara.server.domain.refrigerator.Refrigerator
import mara.server.domain.user.User
import org.springframework.data.domain.Page

data class FriendRefrigeratorDto(
    val user: User,
    val refrigerator: Refrigerator,
    val ingredientDetailList: List<IngredientDetail>
)

data class FriendRefrigeratorResponse(
    val nickname: String,
    val refrigeratorId: Long,
    val friendRefrigeratorIngredientGroupList: Page<FriendRefrigeratorIngredient>
) {
    constructor(user: User, refrigerator: Refrigerator, ingredientList: Page<Ingredient>) : this(
        nickname = user.nickName,
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
