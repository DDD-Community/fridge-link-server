package mara.server.domain.user

import com.querydsl.jpa.impl.JPAQueryFactory
import mara.server.domain.user.QUser.user
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long>, CustomUserRepository {

    fun findByKakaoId(id: Long): User?
    fun findByGoogleEmail(email: String): User?
    fun findByInviteCode(inviteCode: String): User?
    fun existsByNickname(nickname: String): Boolean
}

interface CustomUserRepository {

    fun findByIdPage(userId: Long): List<User>
}

class CustomUserRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : CustomUserRepository {

    override fun findByIdPage(userId: Long): List<User> {
        return queryFactory.selectFrom(user).where(user.userId.eq(userId)).fetch()
    }
}
