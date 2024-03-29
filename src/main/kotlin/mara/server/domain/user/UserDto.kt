package mara.server.domain.user

data class UserRequest(
    val nickname: String,
    val kakaoId: Long?,
    val kakaoEmail: String?,
    val googleEmail: String?,
    val profileImage: String,
)

data class CheckDuplicateResponse(val isDuplicated: Boolean)

data class UserUpdateRequest(val nickname: String, val profileImage: ProfileImage)
class UserResponse(
    val nickname: String?,
    val kakaoId: Long?,
    val kakaoEmail: String?,
    val googleEmail: String?,
    val profileImage: String?,
) {
    constructor(user: User) : this(
        nickname = user.nickname,
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
    val nickname: String,
    val profileImage: ProfileImage,
    val ingredientCount: Long
) {
    constructor(user: User, ingredientCount: Long) : this(
        userId = user.userId,
        nickname = user.nickname,
        profileImage = user.profileImage,
        ingredientCount = ingredientCount
    )
}

class UserStatisticResponse(
    val ingredientCount: Long,
    val shareCount: Long,
    val friendCount: Long
)
