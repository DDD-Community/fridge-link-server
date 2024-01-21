package mara.server.domain.refrigerator

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RefrigeratorRepository : JpaRepository<Refrigerator, Long> {
}