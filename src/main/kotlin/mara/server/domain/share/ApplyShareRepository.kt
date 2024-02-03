package mara.server.domain.share

import mara.server.domain.user.User
import org.springframework.data.jpa.repository.JpaRepository

interface ApplyShareRepository : JpaRepository<ApplyShare, Long> {

    fun findAllByUser(user: User): List<ApplyShare>?

    fun existsByUserAndShare(user: User, share: Share): Boolean
}
