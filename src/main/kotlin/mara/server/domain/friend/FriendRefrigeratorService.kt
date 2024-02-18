package mara.server.domain.friend

import mara.server.domain.ingredient.IngredientDetailQuerydslRepository
import mara.server.domain.refrigerator.RefrigeratorQuerydslRepository
import mara.server.domain.user.UserService
import org.springframework.stereotype.Service

@Service
class FriendRefrigeratorService(
    private val userService: UserService,
    private val friendshipRepository: FriendshipRepository,
    private val refrigeratorQuerydslRepository: RefrigeratorQuerydslRepository,
    private val ingredientDetailQuerydslRepository: IngredientDetailQuerydslRepository
) {

    fun getRecentFriendRefrigeratorList(): List<FriendRefrigeratorResponse> {
        val currentLoginUser = userService.getCurrentLoginUser()
        val friendshipList = friendshipRepository.findByFromUser(currentLoginUser)
            .orElseThrow { NoSuchElementException("친구 관계가 존재하지 않습니다.") }

        val userList = friendshipList.map { it.toUser }
        val refrigeratorList = refrigeratorQuerydslRepository.getRefrigeratorList(userList, 5)

        val friendRefrigeratorResponseList = refrigeratorList.map { refrig ->
            val ingredientDetailList =
                ingredientDetailQuerydslRepository.getIngredientDetailByRefrigerator(refrig, 4)
            val ingredientList = ingredientDetailList.map { it.ingredient }
            FriendRefrigeratorResponse(refrig.user, refrig, ingredientList)
        }

        return friendRefrigeratorResponseList
    }
}
