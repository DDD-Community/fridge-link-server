package mara.server.domain.friend

import mara.server.domain.user.User
import mara.server.domain.user.UserFriendResponse
import mara.server.domain.user.UserRepository
import mara.server.domain.user.UserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FriendshipService(
    private val friendshipRepository: FriendshipRepository,
    private val userRepository: UserRepository,
    private val userService: UserService
) {

    private val ok = "ok"
    private val deleted = "deleted"

    @Transactional
    fun createFriendship(friendshipRequest: FriendshipRequest): String {
        val currentUserId = userService.getCurrentLoginUser().userId
        val fromUser = userRepository.findById(currentUserId)
            .orElseThrow { NoSuchElementException("해당 유저가 존재하지 않습니다. ID: $currentUserId") }
        val toUser = userRepository.findByInviteCode(friendshipRequest.inviteCode)
            .orElseThrow { NoSuchElementException("해당 초대코드를 가진 사용자가 존재하지 않습니다.") }

        addFriendship(fromUser, toUser)
        addFriendship(toUser, fromUser)

        return ok
    }

    fun getFriendshipList(): List<UserFriendResponse> {
        val currentLoginUser = userService.getCurrentLoginUser()
        val friendshipList = friendshipRepository.findAllByFromUser(currentLoginUser)
            .orElseThrow { NoSuchElementException("친구 관계가 존재하지 않습니다.") }

        val userNameList: List<UserFriendResponse> = friendshipList.map { friendship ->
            val userId = friendship.toUser.userId
            val user =
                userRepository.findById(userId).orElseThrow { NoSuchElementException("해당 유저가 존재하지 않습니다. ID: $userId") }
            UserFriendResponse(user)
        }

        return userNameList
    }

    @Transactional
    fun deleteFriendship(friendshipDeleteRequest: FriendshipDeleteRequest): String {
        val currentLoginUser = userService.getCurrentLoginUser()
        val targetUser = userRepository.findById(friendshipDeleteRequest.friendId)
            .orElseThrow { NoSuchElementException("해당 유저가 존재하지 않습니다. ID: ${friendshipDeleteRequest.friendId}") }

        val friendshipList = friendshipRepository.findAllByFromUserAndToUser(currentLoginUser, targetUser)
            .orElseThrow { NoSuchElementException("친구 관계가 존재하지 않습니다.") }

        for (friendship: Friendship in friendshipList) {
            friendshipRepository.delete(friendship)
        }

        return deleted
    }

    private fun addFriendship(user1: User, user2: User) {
        val friendship = Friendship(
            fromUser = user1,
            toUser = user2,
        )
        friendshipRepository.save(friendship)
    }
}
