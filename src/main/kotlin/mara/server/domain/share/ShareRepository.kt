package mara.server.domain.share

import org.springframework.data.jpa.repository.JpaRepository

interface ShareRepository : JpaRepository<Share, Long> {
    fun findAllByUser(userId: Long): List<Share>
}
