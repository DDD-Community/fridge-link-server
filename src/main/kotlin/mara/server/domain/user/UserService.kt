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

    fun createUser(userRequest: UserRequest): Long {
        val user = User(
            nickName = userRequest.nickName,
            kakaoId = userRequest.kakaoId,
            password = passwordEncoder.encode(userRequest.nickName),
            googleEmail = userRequest.googleEmail,
            kakaoEmail = userRequest.kakaoEmail,
            profileImage = Profile.valueOf(userRequest.profileImage),
            inviteCode = StringUtil.generateRandomString(8, 11)
        )
        return userRepository.save(user).userId
    }

    fun checkNickName(nickName: String): CheckDuplicateResponse = CheckDuplicateResponse(userRepository.existsByNickName(nickName))

    fun kakaoLogin(authorizedCode: String): Any {
        // 리다이랙트 url 환경 따라 다르게 전달하기 위한 구분 값
        val accessToken = kakaoApiClient.requestAccessToken(authorizedCode)
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

        return UserResponse(
            nickName = null,
            kakaoEmail = infoResponse.email,
            kakaoId = userName,
            googleEmail = null,
            profileImage = null,
        )
    }

    fun googleLogin(authorizedCode: String): Any {
        val accessToken = googleApiClient.requestAccessToken(authorizedCode)
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

        return UserResponse(
            nickName = null,
            kakaoEmail = null,
            kakaoId = null,
            googleEmail = userName,
            profileImage = null,
        )
    }
}
