package mara.server.domain.user

import com.querydsl.jpa.impl.JPAQueryFactory
import mara.server.domain.user.QUser.user
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface UserRepository : JpaRepository<User, Long>, CustomUserRepository {

    fun findByKakaoId(id: Long): User?
    fun findByGoogleEmail(email: String): User?
    fun findByInviteCode(inviteCode: String): Optional<User>
    fun existsByNickName(nickName: String): Boolean
}

interface CustomUserRepository {

    fun findByIdPage(userId: Long): List<User>
}

class CustomUserRepositoryImpl(
    private val query: JPAQueryFactory
) : CustomUserRepository {

    override fun findByIdPage(userId: Long): List<User> {
        return query.selectFrom(user).where(user.userId.eq(userId)).fetch()
    }
}
