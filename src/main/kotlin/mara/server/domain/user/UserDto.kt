package mara.server.domain.user

data class UserRequest(
    val name: String,
    val kakaoId: Long?,
    val password: String,
    val googleEmail: String?,
)

data class JwtDto(
    val accessToken: String?,
    val refreshToken: String?,
)

class UserResponse(
    val name: String,
    val kakaoId: Long?,
    val googleEmail: String?,
) {
    constructor(user: User) : this(
        name = user.name,
        kakaoId = user?.kakaoId,
        googleEmail = user?.googleEmail
    )
}
