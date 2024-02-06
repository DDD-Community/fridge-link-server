package mara.server.domain.user

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface UserRepository : JpaRepository<User, Long> {

    fun findByKakaoId(id: Long): User?
    fun findByGoogleEmail(email: String): User?
    fun findByInviteCode(inviteCode: String): Optional<User>
    fun existsByNickName(nickName: String): Boolean
}
