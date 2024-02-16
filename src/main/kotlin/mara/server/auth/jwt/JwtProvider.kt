package mara.server.auth.jwt

import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import mara.server.auth.security.PrincipalDetailsService
import mara.server.config.redis.RefreshToken
import mara.server.config.redis.RefreshTokenRepository
import mara.server.domain.user.User
import mara.server.domain.user.UserRepository
import mara.server.util.logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.security.Key
import java.util.Base64
import java.util.Date

@Component
class JwtProvider(
    @Value("\${jwt.secret-key}") private val secretKey: String,
    @Value("\${jwt.access-duration-mils}") private val accessDurationMils: Long,
    private val principalDetailsService: PrincipalDetailsService,
    private val userRepository: UserRepository,
    private val refreshTokenRepository: RefreshTokenRepository
) {
    val key: Key = Keys.hmacShaKeyFor(secretKey.toByteArray())
    val log = logger()
    private val objectMapper = ObjectMapper()
    private val ok: String = "ok"
    private val reissue: String = "reissue"
    private val fail: String = "fail"

    fun generateToken(user: User): String {
        val now = Date(System.currentTimeMillis())
        var claimName = "kakaoId"
        var claimValue = user.kakaoId.toString()
        if (user.googleEmail != null) {
            claimName = "googleEmail"
            claimValue = user.googleEmail!!
        }

        return Jwts.builder()
            .setSubject(user.userId.toString())
            .claim(claimName, claimValue)
            .setIssuedAt(now)
            .setExpiration(Date(now.time + accessDurationMils))
            .signWith(key)
            .compact()
    }

    @Transactional
    fun recreateAccessToken(oldAccessToken: String): String {
        val subject = decodeJwtPayloadSubject(oldAccessToken)
        val user = userRepository.findById(subject.toLong()).get()
        return generateToken(user)
    }

    fun validRefreshToken(refreshToken: String): RefreshToken {
        val token = refreshTokenRepository.findByRefreshToken(refreshToken)
            ?: throw NullPointerException("만료된 RefreshToken 입니다.")
        return token
    }

    fun validate(token: String): String {
        try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
            return ok
        } catch (ex: ExpiredJwtException) {
            log.warn("JWT 토큰 만료 [{}] {}", ex.javaClass.simpleName, ex.message)
            return reissue
        } catch (e: Exception) {
            log.warn("JWT 오류 발생 [{}] {}", e.javaClass.simpleName, e.message)
            return fail
        }
    }

    fun getAuthentication(token: String): Authentication {
        val body = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body

        val userDetails = principalDetailsService.loadUserByUsername(userId = body.subject)
        return UsernamePasswordAuthenticationToken(
            userDetails.username,
            userDetails.password,
            userDetails.authorities,
        )
    }

    private fun decodeJwtPayloadSubject(oldAccessToken: String) =
        objectMapper.readValue(
            Base64.getUrlDecoder().decode(oldAccessToken.split('.')[1]).decodeToString(),
            Map::class.java
        )["sub"].toString()
}
