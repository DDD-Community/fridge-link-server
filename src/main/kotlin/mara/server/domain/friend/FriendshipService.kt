package mara.server.domain.friend

import mara.server.domain.user.User
import mara.server.domain.user.UserNameResponse
import mara.server.domain.user.UserRepository
import mara.server.domain.user.UserService
import org.springframework.stereotype.Service

@Service
class FriendshipService(
    private val friendshipRepository: FriendshipRepository,
    private val userRepository: UserRepository,
    private val userService: UserService
) {

    fun createFriendship(friendshipRequest: FriendshipRequest): String {
        val currentUserId = userService.getCurrentLoginUser().userId
        val fromUser = userRepository.findById(currentUserId)
            .orElseThrow { NoSuchElementException("해당 유저가 존재하지 않습니다. ID: $currentUserId") }
        val toUser = userRepository.findByInviteCode(friendshipRequest.inviteCode)
            .orElseThrow { NoSuchElementException("해당 초대코드를 가진 사용자가 존재하지 않습니다.") }

        addFriendship(fromUser, toUser)
        addFriendship(toUser, fromUser)

        return "ok"
    }

    fun getFriendshipList(): List<UserNameResponse> {
        val currentLoginUser = userService.getCurrentLoginUser()
        val friendshipList = friendshipRepository.findAllByFromUserAndIsFriend(currentLoginUser)
            .orElseThrow { NoSuchElementException("친구 관계가 존재하지 않습니다.") }

        val userNameList: List<UserNameResponse> = friendshipList.map { friendship ->
            val userId = friendship.toUser.userId
            val user =
                userRepository.findById(userId).orElseThrow { NoSuchElementException("해당 유저가 존재하지 않습니다. ID: $userId") }
            UserNameResponse(user)
        }

        return userNameList
    }

    private fun addFriendship(user1: User, user2: User) {
        val friendship2 = Friendship(
            fromUser = user1,
            toUser = user2,
            isFriend = true
        )
        friendshipRepository.save(friendship2)
    }
}
