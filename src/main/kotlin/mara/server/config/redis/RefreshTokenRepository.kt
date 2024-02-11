package mara.server.config.redis

import org.springframework.data.repository.CrudRepository

interface RefreshTokenRepository : CrudRepository<RefreshToken, String> {
    fun findByRefreshToken(refreshToken: String?): RefreshToken?
}
