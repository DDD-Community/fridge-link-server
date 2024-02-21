package mara.server.domain.friend

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import mara.server.common.CommonResponse
import mara.server.common.success
import mara.server.domain.user.UserFriendResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/friendship")
@Tag(name = "친구", description = "친구 API")
class FriendshipController(
    private val friendshipService: FriendshipService
) {

    @PostMapping
    @Operation(summary = "친구 맺기 API")
    fun createFriendship(@RequestBody friendshipRequest: FriendshipRequest): CommonResponse<String> {
        return success(friendshipService.createFriendship(friendshipRequest))
    }

    @GetMapping
    @Operation(summary = "친구 조회 API")
    fun getFriendshipList(
        @PageableDefault(
            size = 10,
            sort = ["createdAt"]
        )
        pageable: Pageable
    ): CommonResponse<Page<UserFriendResponse>> {
        return success(friendshipService.getFriendshipList(pageable))
    }

    @GetMapping("/count")
    @Operation(summary = "친구 수 조회 API")
    fun getFriendshipCount(): CommonResponse<Long> {
        return success(friendshipService.getFriendshipCount())
    }

    @PostMapping("/delete")
    @Operation(summary = "친구 삭제 API")
    fun deleteFriendship(@RequestBody friendshipDeleteRequestList: List<FriendshipDeleteRequest>): CommonResponse<String> {
        return success(friendshipService.deleteFriendship(friendshipDeleteRequestList))
    }
}
