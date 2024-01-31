package mara.server.domain.friend

import mara.server.common.CommonResponse
import mara.server.common.success
import mara.server.domain.user.UserNameResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
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
    fun getFriendshipList(): CommonResponse<List<UserNameResponse>> {
        return success(friendshipService.getFriendshipList())
    }

    @PutMapping
    fun updateFriendship(@RequestBody friendshipUpdateRequest: FriendshipUpdateRequest): CommonResponse<String> {
        return success(friendshipService.updateFriendship(friendshipUpdateRequest))
    }
}
