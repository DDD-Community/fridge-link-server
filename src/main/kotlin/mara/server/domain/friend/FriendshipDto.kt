package mara.server.domain.friend

data class FriendshipRequest(
    val inviteCode: String
)

data class FriendshipDeleteRequest(
    val friendId: Long
)
