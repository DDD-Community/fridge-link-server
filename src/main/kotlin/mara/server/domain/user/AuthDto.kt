package mara.server.domain.user

sealed class AuthDto

data class JwtDto(
    val accessToken: String?,
    val refreshToken: String?,
) : AuthDto()

data class KakaoAuthInfo(
    val kakaoId: Long,
    val kakaoEmail: String,
) : AuthDto()

data class GoogleAuthInfo(
    val googleEmail: String,
) : AuthDto()
