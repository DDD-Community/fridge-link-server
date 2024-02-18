package mara.server.domain.share

import com.querydsl.core.types.OrderSpecifier
import com.querydsl.jpa.impl.JPAQueryFactory
import mara.server.domain.friend.QFriendship.friendship
import mara.server.domain.share.QApplyShare.applyShare
import mara.server.domain.share.QShare.share
import mara.server.domain.user.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.support.PageableExecutionUtils

interface ShareRepository : JpaRepository<Share, Long>, CustomShareRepository

interface CustomShareRepository {
    fun findAllMyFriendsShare(pageable: Pageable, sortBy: String, user: User): Page<Share>

    fun findAllMyShare(pageable: Pageable, sortBy: String, status: ShareStatus, user: User): Page<Share>

    fun findAllMyAppliedShare(pageable: Pageable, sortBy: String, status: ShareStatus, user: User): Page<Share>
}

class CustomShareRepositoryImpl(
    private val queryFactory: JPAQueryFactory,
) : CustomShareRepository {

    private val registeredDate: String = "registeredDate"
    private val dueDate: String = "dueDate"

    override fun findAllMyFriendsShare(pageable: Pageable, sortBy: String, user: User): Page<Share> {
        val friendsList = queryFactory.select(friendship.toUser.userId)
            .from(friendship)
            .where(friendship.fromUser.eq(user))

        val query = queryFactory.selectFrom(share)
            .where(share.user.userId.`in`(friendsList))
            .orderBy(getOrder(sortBy)).fetch()

        val count = queryFactory.select(share.count()).from(share).where(share.user.userId.`in`(friendsList)).fetchOne()

        return PageableExecutionUtils.getPage(query, pageable) { count!! }
    }

    override fun findAllMyShare(pageable: Pageable, sortBy: String, status: ShareStatus, user: User): Page<Share> {
        val query = queryFactory.selectFrom(share)
            .where(share.user.eq(user).and(share.status.eq(status)))
            .orderBy(getOrder(sortBy))
            .fetch()

        val count = queryFactory.select(share.count()).from(share).where(share.user.eq(user).and(share.status.eq(status))).fetchOne()

        return PageableExecutionUtils.getPage(query, pageable) { count!! }
    }

    override fun findAllMyAppliedShare(
        pageable: Pageable,
        sortBy: String,
        status: ShareStatus,
        user: User
    ): Page<Share> {

        val query = queryFactory.select(share).from(applyShare)
            .join(applyShare.share, share)
            .where(applyShare.user.eq(user).and(share.status.eq(status)))
            .orderBy(getOrder(sortBy))
            .fetch()

        val count = queryFactory.select(share.count()).from(applyShare)
            .join(applyShare.share, share)
            .where(applyShare.user.eq(user).and(share.status.eq(status)))
            .fetchOne()

        return PageableExecutionUtils.getPage(query, pageable) { count!! }
    }

    private fun getOrder(sortBy: String): OrderSpecifier<*> {
        val orderSpecifier = when (sortBy) {
            registeredDate -> share.createdAt.desc()
            dueDate -> share.shareDatetime.asc()
            else -> throw IllegalArgumentException("Invalid sortBy value")
        }
        return orderSpecifier
    }
}
