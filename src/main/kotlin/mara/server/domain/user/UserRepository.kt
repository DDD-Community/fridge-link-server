package mara.server.domain.user

import com.querydsl.jpa.impl.JPAQueryFactory
import mara.server.domain.user.QUser.user
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

interface CustomUserRepository {

    fun findByIdPage(userId: Long): List<User>
}

@Repository
class CustomUserRepositoryImpl(
    private val query: JPAQueryFactory
) : CustomUserRepository {

    override fun findByIdPage(userId: Long): List<User> {
        return query.selectFrom(user).where(user.userId.eq(userId)).fetch()
    }
}
