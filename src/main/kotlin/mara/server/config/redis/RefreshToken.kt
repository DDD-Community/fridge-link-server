package mara.server.config.redis

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive
import org.springframework.data.redis.core.index.Indexed
import java.util.concurrent.TimeUnit

@RedisHash(value = "refreshToken")
data class RefreshToken(
    @Id
    @Indexed
    val refreshToken: String,

    val userId: Long,

    @TimeToLive(unit = TimeUnit.MINUTES)
    val expiration: Int,
)
