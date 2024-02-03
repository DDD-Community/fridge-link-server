package mara.server.domain.user

data class UserRequest(
    val nickName: String,
    val kakaoId: Long?,
    val googleEmail: String?,
    val kakaoEmail: String?,
    val profileImage: String,
)

data class JwtDto(
    val accessToken: String?,
    val refreshToken: String?,
)

data class CheckDuplicateResponse(val isDuplicated: Boolean)

class UserResponse(
    val nickName: String?,
    val kakaoId: Long?,
    val googleEmail: String?,
    val kakaoEmail: String?,
    val profileImage: String?,
) {
    constructor(user: User) : this(
        nickName = user.nickName,
        kakaoId = user.kakaoId,
        googleEmail = user.googleEmail,
        kakaoEmail = user.kakaoEmail,
        profileImage = user.profileImage,
    )
}

class UserInviteCodeResponse(
    val inviteCode: String
) {
    constructor(user: User) : this(
        inviteCode = user.inviteCode
    )
}

class UserNameResponse(
    val userId: Long,
    val nickName: String
) {
    constructor(user: User) : this(
        userId = user.userId,
        nickName = user.nickName
    )
}
