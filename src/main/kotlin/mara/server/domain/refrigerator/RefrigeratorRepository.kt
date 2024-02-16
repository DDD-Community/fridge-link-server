package mara.server.domain.refrigerator

import mara.server.domain.user.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RefrigeratorRepository : JpaRepository<Refrigerator, Long> {
    fun findRefrigeratorsByUser(user: User): List<Refrigerator>

    fun findRefrigeratorByUserIn(user: List<User>, pageable: Pageable): Page<Refrigerator>
}
