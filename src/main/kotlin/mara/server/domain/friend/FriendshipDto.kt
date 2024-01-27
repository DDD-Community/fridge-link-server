package mara.server.domain.friend

data class FriendshipRequest(
    val inviteCode: String
)

data class FriendshipUpdateRequest(
    val friendId: Long,
    val isFriend: Boolean
)

data class FriendshipResponse(
    val friendshipId: Long,
    val friendId: Long
)