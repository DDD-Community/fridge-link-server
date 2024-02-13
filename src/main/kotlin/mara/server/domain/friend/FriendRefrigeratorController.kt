package mara.server.domain.friend

import mara.server.common.CommonResponse
import mara.server.common.success
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/friend-refrigs")
class FriendRefrigeratorController() {

    @GetMapping
    fun getFriendRefrigeratorList(): CommonResponse<Long> {
        return success(1)
    }

    @GetMapping
    fun getRecentFriendIngredientDetailList(): CommonResponse<Long> {
        return success(2)
    }
}
