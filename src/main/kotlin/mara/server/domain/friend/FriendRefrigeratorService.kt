package mara.server.domain.friend

import mara.server.domain.ingredient.CustomIngredientDetailRepositoryImpl
import mara.server.domain.refrigerator.CustomRefrigeratorRepositoryImpl
import mara.server.domain.user.UserService
import org.springframework.stereotype.Service

@Service
class FriendRefrigeratorService(
    private val userService: UserService,
    private val friendshipRepository: FriendshipRepository,
    private val customRefrigeratorRepositoryImpl: CustomRefrigeratorRepositoryImpl,
    private val customIngredientDetailRepositoryImpl: CustomIngredientDetailRepositoryImpl
) {

    fun getRecentFriendRefrigeratorList(): List<FriendRefrigeratorResponse> {
        val currentLoginUser = userService.getCurrentLoginUser()
        val friendshipList = friendshipRepository.findByFromUser(currentLoginUser)
            .orElseThrow { NoSuchElementException("친구 관계가 존재하지 않습니다.") }

        val userList = friendshipList.map { it.toUser }
        val refrigeratorList = customRefrigeratorRepositoryImpl.findByUserList(userList, 5)

        val friendRefrigeratorResponseList = refrigeratorList.map { refrig ->
            val ingredientDetailList =
                customIngredientDetailRepositoryImpl.findByRefrigerator(refrig, 4)
            val ingredientList = ingredientDetailList.map { it.ingredient }
            FriendRefrigeratorResponse(refrig.user, refrig, ingredientList)
        }

        return friendRefrigeratorResponseList
    }
}
