package mara.server.domain.friend

import mara.server.common.CommonResponse
import mara.server.common.success
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/friend-refrigs")
class FriendRefrigeratorController(
    private val friendRefrigeratorService: FriendRefrigeratorService
) {
    @GetMapping("/recent")
    fun getRecentFriendRefrigeratorList(): CommonResponse<List<FriendRefrigeratorResponse>> {
        return success(friendRefrigeratorService.getRecentFriendRefrigeratorList())
    }
}
