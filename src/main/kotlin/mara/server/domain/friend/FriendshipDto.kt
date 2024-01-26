package mara.server.domain.friend

data class FriendshipRequest(
    val inviteCode: String
)

data class FriendshipUpdateRequest(
    val friendId: Long,
    val status: Boolean
)

// TODO FriendshipResponse