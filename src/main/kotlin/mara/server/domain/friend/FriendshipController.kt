package mara.server.domain.friend

import mara.server.common.CommonResponse
import mara.server.common.success
import mara.server.domain.user.UserFriendResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/friendship")
class FriendshipController(
    private val friendshipService: FriendshipService
) {

    @PostMapping
    fun createFriendship(@RequestBody friendshipRequest: FriendshipRequest): CommonResponse<String> {
        return success(friendshipService.createFriendship(friendshipRequest))
    }

    @GetMapping
    fun getFriendshipList(): CommonResponse<List<UserFriendResponse>> {
        return success(friendshipService.getFriendshipList())
    }

    @GetMapping("/count")
    fun getFriendshipCount(): CommonResponse<Long> {
        return success(friendshipService.getFriendshipCount())
    }

    @PostMapping("/delete")
    fun deleteFriendship(@RequestBody friendshipDeleteRequestList: List<FriendshipDeleteRequest>): CommonResponse<String> {
        return success(friendshipService.deleteFriendship(friendshipDeleteRequestList))
    }
}
