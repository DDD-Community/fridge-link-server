package mara.server.domain.friend

import com.querydsl.jpa.impl.JPAQueryFactory
import mara.server.domain.friend.QFriendship.friendship
import mara.server.domain.user.QUser
import mara.server.domain.user.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.support.PageableExecutionUtils
import java.util.Optional

interface FriendshipRepository : JpaRepository<Friendship, Long>, CustomFriendshipRepository {
    fun findByFromUser(
        user: User,
    ): Optional<List<Friendship>>

    fun countByFromUser(
        user: User
    ): Long
}

interface CustomFriendshipRepository {

    fun findByFromUserPage(user: User, pageable: Pageable): Page<Friendship>

    fun findByFromUserAndToUser(fromUser: User, toUser: User): List<Friendship>
}

class CustomFriendshipRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : CustomFriendshipRepository {

    private val createdAt: String = "createdAt"
    private val nickname: String = "nickname"

    override fun findByFromUserPage(user: User, pageable: Pageable): Page<Friendship> {
        val appUser = QUser.user
        val query = queryFactory.select(friendship).from(friendship).innerJoin(friendship.toUser, appUser).on(
            friendship.toUser.userId.eq(appUser.userId)
        ).where(friendship.fromUser.eq(user))
            .offset(pageable.offset).limit(pageable.pageSize.toLong())

        pageable.sort.forEach { order ->
            val path = when (order.property) {
                createdAt -> friendship.createdAt.asc()
                nickname -> appUser.nickname.asc()
                else -> throw IllegalArgumentException("Unsupported sorting property: ${order.property}")
            }
            query.orderBy(path)
        }

        val results = query.fetch()

        val count = queryFactory.select(friendship.count()).from(friendship).innerJoin(friendship.toUser, appUser).on(
            friendship.toUser.userId.eq(appUser.userId)
        ).where(friendship.fromUser.eq(user))
            .offset(pageable.offset).limit(pageable.pageSize.toLong()).fetchOne() ?: 0

        return PageableExecutionUtils.getPage(results, pageable) { count }
    }

    override fun findByFromUserAndToUser(fromUser: User, toUser: User): List<Friendship> {
        return queryFactory.selectFrom(friendship).where(
            friendship.fromUser.eq(fromUser).and(friendship.toUser.eq(toUser))
                .or(friendship.fromUser.eq(toUser).and(friendship.toUser.eq(fromUser)))
        ).fetch()
    }
}
