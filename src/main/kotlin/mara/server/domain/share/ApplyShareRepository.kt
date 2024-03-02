package mara.server.domain.share

import mara.server.domain.user.User
import org.springframework.data.jpa.repository.JpaRepository

interface ApplyShareRepository : JpaRepository<ApplyShare, Long> {
    fun existsByUserAndShare(user: User, share: Share): Boolean

    fun findByUserAndShare(user: User, share: Share): ApplyShare
    fun countByUser(user: User): Long
}
