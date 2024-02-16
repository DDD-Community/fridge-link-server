package mara.server.domain.friend

import mara.server.common.CommonResponse
import mara.server.common.success
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/friend-refrigs")
class FriendRefrigeratorController(
    private val friendRefrigeratorService: FriendRefrigeratorService
) {

    @GetMapping("/recent")
    fun getRecentFriendRefrigeratorList(
        @Qualifier("userPageable")
        @PageableDefault(
            size = 5, sort = ["ingredientAddDate"], direction = Sort.Direction.DESC
        )
        userPageable: Pageable,
        @Qualifier("ingredientPageable")
        @PageableDefault(
            size = 4, sort = ["addDate"], direction = Sort.Direction.DESC
        )
        ingredientPageable: Pageable,
    ): CommonResponse<Page<FriendRefrigeratorResponse>> {
        return success(friendRefrigeratorService.getRecentFriendRefrigeratorList(userPageable, ingredientPageable))
    }
}
