package mara.server.domain.user

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {

    fun findByKakaoId(id: Long): User?
    fun findByGoogleEmail(email: String): User?
}
