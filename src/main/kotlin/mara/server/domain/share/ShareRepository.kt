package mara.server.domain.share

import mara.server.domain.user.User
import org.springframework.data.jpa.repository.JpaRepository

interface ShareRepository : JpaRepository<Share, Long> {
    fun findAllByUser(user: User): List<Share>?
}
