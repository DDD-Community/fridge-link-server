package mara.server.domain.friend

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import mara.server.common.CommonResponse
import mara.server.common.success
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/friend-refrigs")
@Tag(name = "친구 냉장고", description = "친구 냉장고 API")
class FriendRefrigeratorController(
    private val friendRefrigeratorService: FriendRefrigeratorService
) {
    @GetMapping("/recent")
    @Operation(summary = "친구 냉장고 최신 근황 조회 API")
    fun getRecentFriendRefrigeratorList(): CommonResponse<List<FriendRefrigeratorResponse>> {
        return success(friendRefrigeratorService.getRecentFriendRefrigeratorList())
    }
}
