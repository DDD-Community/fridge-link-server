package mara.server.domain.friend

import com.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer.FromIntegerArguments
import mara.server.domain.user.UserRepository
import mara.server.domain.user.UserService
import org.springframework.stereotype.Service

@Service
class FriendshipService(
    private val friendshipRepository: FriendshipRepository,
    private val userRepository: UserRepository,
    private val userService: UserService
) {

    fun createFriendship(friendshipRequest: FriendshipRequest): Long {
        val currentUserId = userService.getCurrentLoginUser().userId
        val fromUser = userRepository.findById(currentUserId).orElseThrow { NoSuchElementException("해당 유저가 존재하지 않습니다. ID: $currentUserId") }
        val toUser = userRepository.findByInviteCode(friendshipRequest.inviteCode).orElseThrow { NoSuchElementException("해당 초대코드를 가진 사용자가 존재하지 않습니다.") }

        val friendship = Friendship(
            fromUser = fromUser,
            toUser = toUser,
            isFriend = true
        )
        return friendshipRepository.save(friendship).friendshipId
    }

    fun getFriendShipList(): List<Friendship> {
        val currentLoginUser = userService.getCurrentLoginUser()
        val friendshipList = friendshipRepository.findAllByIsFriend(currentLoginUser).orElseThrow { NoSuchElementException("친구 관계가 존재하지 않습니다.") }
        return friendshipList
    }

}