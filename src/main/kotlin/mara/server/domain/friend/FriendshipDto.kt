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
    val fromFriend: Long,
    val toFriend: Long
) {
    constructor(friendship: Friendship) : this(
        friendshipId = friendship.friendshipId,
        fromFriend = friendship.fromUser.userId,
        toFriend = friendship.toUser.userId
    )
}

fun List<Friendship>.toFriendshipResponseList(): List<FriendshipResponse> {
    return this.map { FriendshipResponse(it) }
}
