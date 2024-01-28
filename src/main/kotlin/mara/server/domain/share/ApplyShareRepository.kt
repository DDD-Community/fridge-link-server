package mara.server.domain.share

import org.springframework.data.jpa.repository.JpaRepository

interface ApplyShareRepository : JpaRepository<ApplyShare, Long> {

    fun findAllByUser(userId: Long): List<ApplyShare>
}
