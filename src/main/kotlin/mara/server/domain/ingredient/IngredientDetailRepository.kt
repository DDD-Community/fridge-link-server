package mara.server.domain.ingredient

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface IngredientDetailRepository : JpaRepository<IngredientDetail, Long> {
}