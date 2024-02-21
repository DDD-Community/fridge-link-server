package mara.server.domain.user

data class UserRequest(
    val nickName: String,
    val kakaoId: Long?,
    val kakaoEmail: String?,
    val googleEmail: String?,
    val profileImage: String,
)

data class CheckDuplicateResponse(val isDuplicated: Boolean)

data class RefreshAccessTokenRequest(val refreshToken: String)
class UserResponse(
    val nickName: String?,
    val kakaoId: Long?,
    val kakaoEmail: String?,
    val googleEmail: String?,
    val profileImage: String?,
) {
    constructor(user: User) : this(
        nickName = user.nickName,
        kakaoId = user.kakaoId,
        googleEmail = user.googleEmail,
        kakaoEmail = user.kakaoEmail,
        profileImage = user.profileImage.name,
    )
}

class UserInviteCodeResponse(
    val inviteCode: String
) {
    constructor(user: User) : this(
        inviteCode = user.inviteCode
    )
}

class UserFriendResponse(
    val userId: Long,
    val nickName: String,
    val ingredientCount: Long
) {
    constructor(user: User, ingredientCount: Long) : this(
        userId = user.userId,
        nickName = user.nickName,
        ingredientCount = ingredientCount
    )
}
