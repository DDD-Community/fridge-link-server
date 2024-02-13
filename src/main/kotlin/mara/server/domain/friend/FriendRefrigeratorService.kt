package mara.server.domain.friend

import mara.server.domain.ingredient.IngredientDetailRepository
import mara.server.domain.ingredient.IngredientDetailResponse
import mara.server.domain.ingredient.toIngredientDetailResponseList
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

    fun getRecentFriendRefrigeratorList(): List<IngredientDetailResponse> {
        // 친구 id 리스트 조회
        val currentLoginUser = userService.getCurrentLoginUser()
        val friendshipList = friendshipRepository.findAllByFromUser(currentLoginUser)
            .orElseThrow { NoSuchElementException("친구 관계가 존재하지 않습니다.") }

        val userList = friendshipList.map { it.toUser }
        val refrigeratorList = refrigeratorRepository.findRefrigeratorByUserInOrderByIngredientAddDateDesc(userList)

        val ingredientDetailList =
            ingredientDetailRepository.findIngredientDetailsByRefrigeratorAndIsDeletedIsFalse(refrigeratorList[0])
                .orElseThrow { NoSuchElementException("해당 식재료 상세가 존재하지 않습니다.") }
        // 친구 중 가장 최근에 식재료를 추가한 5명의 친구 및 냉장고ID 선별
        return ingredientDetailList.toIngredientDetailResponseList()
        // 반복문 시작
        // 그 중 한 명의 친구가 가지고 있는 냉장고의 모든 상세 식재료 조회 (5개 제한, 최근 순서대로 정렬)

//        return 1
    }
}
