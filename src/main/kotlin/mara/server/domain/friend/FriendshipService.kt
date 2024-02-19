package mara.server.domain.friend

import mara.server.domain.user.User
import mara.server.domain.user.UserFriendResponse
import mara.server.domain.user.UserRepository
import mara.server.domain.user.UserService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FriendshipService(
    private val friendshipRepository: FriendshipRepository,
    private val customFriendshipRepositoryImpl: CustomFriendshipRepositoryImpl,
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

    fun getFriendshipList(pageable: Pageable): Page<UserFriendResponse> {
        val currentLoginUser = userService.getCurrentLoginUser()
        val friendshipList = customFriendshipRepositoryImpl.findByFromUserPage(currentLoginUser, pageable)
        val userFriendResponseList = friendshipList.map { friendship ->
            val userId = friendship.toUser.userId
            val user =
                userRepository.findById(userId).orElseThrow { NoSuchElementException("해당 유저가 존재하지 않습니다. ID: $userId") }
            UserFriendResponse(user)
        }

        return userFriendResponseList
    }

    fun getFriendshipCount(): Long {
        val currentLoginUser = userService.getCurrentLoginUser()
        return friendshipRepository.countByFromUser(currentLoginUser)
    }

    @Transactional
    fun deleteFriendship(friendshipDeleteRequestList: List<FriendshipDeleteRequest>): String {
        val currentLoginUser = userService.getCurrentLoginUser()

        for (friendshipDeleteRequest in friendshipDeleteRequestList) {
            val targetUser = userRepository.findById(friendshipDeleteRequest.friendId)
                .orElseThrow { NoSuchElementException("해당 유저가 존재하지 않습니다. ID: ${friendshipDeleteRequest.friendId}") }

            val friendshipList = customFriendshipRepositoryImpl.findByFromUserAndToUser(currentLoginUser, targetUser)
            friendshipList.forEach { friendshipRepository.delete(it) }
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
