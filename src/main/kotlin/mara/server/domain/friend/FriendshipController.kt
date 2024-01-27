package mara.server.domain.friend

import mara.server.common.CommonResponse
import mara.server.common.success
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
    fun createFriendship(@RequestBody friendshipRequest: FriendshipRequest): CommonResponse<Long> {
        return success(friendshipService.createFriendship(friendshipRequest))
    }

    @GetMapping("{/id}")
    fun getFriendship(@PathVariable(name = "id") id: Long): CommonResponse<Long> {
        return success(1)
    }

    // TODO FrindshipResponse 로 수정 필요
    @GetMapping
    fun getFriendshipList(): CommonResponse<List<FriendshipResponse>> {
        return success(friendshipService.getFriendShipList())
    }

    // 친구 삭제 기능 수행
    @PutMapping
    fun updateFriendship(@RequestBody friendshipUpdateRequest: FriendshipUpdateRequest): CommonResponse<Long> {
        return success(1)
    }
}