package mara.server.domain.user

import mara.server.auth.google.GoogleApiClient
import mara.server.auth.jwt.JwtProvider
import mara.server.auth.kakao.KakaoApiClient
import mara.server.auth.security.getCurrentLoginUserId
import mara.server.util.StringUtil
import mara.server.util.logger
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
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

    fun getCurrentLoginUserInviteCode() = UserInviteCodeResponse(getCurrentLoginUser())

    @Transactional
    fun singUp(user: UserRequest): JwtDto {
        val newUser = createUser(user)

        var authId = newUser.kakaoId.toString()
        if (newUser.googleEmail != null) authId = newUser.googleEmail!!

        SecurityContextHolder.getContext().authentication =
            UsernamePasswordAuthenticationToken(authId, newUser.password)
        val refreshToken = UUID.randomUUID().toString()

        // JWT 발급
        return JwtDto(jwtProvider.generateToken(newUser), refreshToken)
    }
    private fun createUser(userRequest: UserRequest): User {
        val user = User(
            nickName = userRequest.nickName,
            kakaoId = userRequest.kakaoId,
            password = passwordEncoder.encode(userRequest.nickName),
            googleEmail = userRequest.googleEmail,
            kakaoEmail = userRequest.kakaoEmail,
            profileImage = ProfileImage.valueOf(userRequest.profileImage),
            inviteCode = StringUtil.generateRandomString(8, 11)
        )
        return userRepository.save(user)
    }

    fun checkNickName(nickName: String): CheckDuplicateResponse = CheckDuplicateResponse(userRepository.existsByNickName(nickName))

    fun kakaoLogin(authorizedCode: String): AuthDto {
        // 리다이랙트 url 환경 따라 다르게 전달하기 위한 구분 값
        val accessToken = kakaoApiClient.requestAccessToken(authorizedCode)
        val oauthInfoResponse = kakaoApiClient.requestOauthInfo(accessToken)
        log.info("kakaoId ? " + oauthInfoResponse.id)
        val user = userRepository.findByKakaoId(oauthInfoResponse.id)

        val authId = oauthInfoResponse.id

        // password 는 서비스에서 사용X, Security 설정을 위해 넣어준 값
        val password = authId.toString()

        if (user != null) {
            SecurityContextHolder.getContext().authentication =
                UsernamePasswordAuthenticationToken(authId, password)

            val refreshToken = UUID.randomUUID().toString()
            return JwtDto(jwtProvider.generateToken(user), refreshToken)
        }

        return KakaoAuthInfo(
            kakaoId = authId,
            kakaoEmail = oauthInfoResponse.email
        )
    }

    fun googleLogin(authorizedCode: String): AuthDto {
        val accessToken = googleApiClient.requestAccessToken(authorizedCode)
        val oauthInfoResponse = googleApiClient.requestOauthInfo(accessToken)

        log.info(oauthInfoResponse.email)

        val user = userRepository.findByGoogleEmail(oauthInfoResponse.email)

        val authId = oauthInfoResponse.email

        if (user != null) {
            SecurityContextHolder.getContext().authentication =
                UsernamePasswordAuthenticationToken(authId, authId)

            val refreshToken = UUID.randomUUID().toString()
            return JwtDto(jwtProvider.generateToken(user), refreshToken)
        }

        return GoogleAuthInfo(
            googleEmail = authId,
        )
    }
}
