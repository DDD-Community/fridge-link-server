package mara.server.domain.user

import mara.server.auth.google.GoogleApiClient
import mara.server.auth.jwt.JwtProvider
import mara.server.auth.kakao.KakaoApiClient
import mara.server.auth.security.getCurrentLoginUserId
import mara.server.util.logger
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class UserService(
    private val userRepository: UserRepository,
    private val jwtProvider: JwtProvider,
    private val passwordEncoder: BCryptPasswordEncoder,
    private val kakaoApiClient: KakaoApiClient,
    private val googleApiClient: GoogleApiClient,
) {

    val log = logger()

    fun getCurrentLoginUser(): User = userRepository.findById(getCurrentLoginUserId()).orElseThrow()

    fun getCurrentUserInfo() = UserResponse(getCurrentLoginUser())

    fun createUser(userRequest: UserRequest): Long {
        val user = User(
            name = userRequest.name,
            kakaoId = userRequest.kakaoId,
            password = passwordEncoder.encode(userRequest.name),
            googleEmail = userRequest.googleEmail,
        )
        return userRepository.save(user).userId
    }

    fun kakaoLogin(authorizedCode: String): JwtDto {
        // 리다이랙트 url 환경 따라 다르게 전달하기 위한 구분 값
        val status = ""
        val accessToken = kakaoApiClient.requestAccessToken(authorizedCode, status)
        val infoResponse = kakaoApiClient.requestOauthInfo(accessToken)
        log.info("kakaoId ? " + infoResponse.id)
        val user = userRepository.findByKakaoId(infoResponse.id)

        val userName = infoResponse.id

        // password 는 서비스에서 사용X, Security 설정을 위해 넣어준 값
        val password = userName.toString()

        if (user != null) {
            SecurityContextHolder.getContext().authentication =
                UsernamePasswordAuthenticationToken(userName, password)

            val refreshToken = UUID.randomUUID().toString()
            return JwtDto(jwtProvider.generateToken(user), refreshToken)
        }

        return JwtDto(null, null)
    }

    fun googleLogin(authorizedCode: String): JwtDto {
        val status = ""
        val accessToken = googleApiClient.requestAccessToken(authorizedCode, status)
        val infoResponse = googleApiClient.requestOauthInfo(accessToken)

        log.info(infoResponse.email)

        val user = userRepository.findByGoogleEmail(infoResponse.email)

        val userName = infoResponse.email

        if (user != null) {
            SecurityContextHolder.getContext().authentication =
                UsernamePasswordAuthenticationToken(userName, userName)

            val refreshToken = UUID.randomUUID().toString()
            return JwtDto(jwtProvider.generateToken(user), refreshToken)
        }

        return JwtDto(null, null)
    }
}
