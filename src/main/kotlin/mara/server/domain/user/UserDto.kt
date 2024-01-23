package mara.server.domain.user

data class UserRequest(
    val name: String,
    val kaKaoId: Long?,
    val password: String,
    val googleEmail: String?,
)

data class JwtDto(
    val accessToken: String?,
    val refreshToken: String?,
)

class UserResponseDto(
    val name: String,
    val kaKaoId: Long?,
    val googleEmail: String?,
) {
    constructor(user: User) : this(
        name = user.name,
        kaKaoId = user?.kaKaoId,
        googleEmail = user?.googleEmail
    )
}
