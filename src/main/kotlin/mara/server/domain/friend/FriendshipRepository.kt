package mara.server.domain.friend

import com.querydsl.jpa.impl.JPAQueryFactory
import mara.server.domain.friend.QFriendship.friendship
import mara.server.domain.user.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface FriendshipRepository : JpaRepository<Friendship, Long> {
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

@Repository
class CustomFriendshipRepositoryImpl(
    private val query: JPAQueryFactory
) : CustomFriendshipRepository {

    override fun findByFromUserPage(user: User, pageable: Pageable): Page<Friendship> {
        val results = query.select(friendship).from(friendship).where(friendship.fromUser.eq(user))
            .offset(pageable.offset).limit(pageable.pageSize.toLong()).fetch()

        val count = query.select(friendship.count()).from(friendship)
            .where(friendship.fromUser.eq(user))
            .offset(pageable.offset).limit(pageable.pageSize.toLong()).orderBy(friendship.createdAt.asc()).fetchOne() ?: 0

        return PageImpl(results, pageable, count)
    }

    override fun findByFromUserAndToUser(fromUser: User, toUser: User): List<Friendship> {
        return query.selectFrom(friendship).where(
            friendship.fromUser.eq(fromUser).and(friendship.toUser.eq(toUser))
                .or(friendship.fromUser.eq(toUser).and(friendship.toUser.eq(fromUser)))
        ).fetch()
    }
}
