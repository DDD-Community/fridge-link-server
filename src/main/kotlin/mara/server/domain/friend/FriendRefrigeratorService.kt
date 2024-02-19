package mara.server.domain.friend

import mara.server.domain.ingredient.IngredientDetailRepository
import mara.server.domain.refrigerator.RefrigeratorRepository
import mara.server.domain.user.UserService
import org.springframework.stereotype.Service

@Service
class FriendRefrigeratorService(
    private val userService: UserService,
    private val friendshipRepository: FriendshipRepository,
    private val refrigeratorRepository: RefrigeratorRepository,
    private val ingredientDetailRepository: IngredientDetailRepository
) {

    fun getRecentFriendRefrigeratorList(): List<FriendRefrigeratorResponse> {
        val currentLoginUser = userService.getCurrentLoginUser()
        val friendshipList = friendshipRepository.findByFromUser(currentLoginUser)
            .orElseThrow { NoSuchElementException("친구 관계가 존재하지 않습니다.") }

        val userList = friendshipList.map { it.toUser }
        val refrigeratorList = refrigeratorRepository.findByUserList(userList, 5)

        val friendRefrigeratorResponseList = refrigeratorList.map { refrig ->
            val ingredientDetailList =
                ingredientDetailRepository.findByRefrigerator(refrig, 4)
            val ingredientList = ingredientDetailList.map { it.ingredient }
            FriendRefrigeratorResponse(refrig.user, refrig, ingredientList)
        }

        return friendRefrigeratorResponseList
    }
}
