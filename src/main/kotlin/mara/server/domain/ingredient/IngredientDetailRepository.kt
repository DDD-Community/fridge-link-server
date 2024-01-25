package mara.server.domain.ingredient

import mara.server.domain.refrigerator.Refrigerator
import mara.server.domain.user.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface IngredientDetailRepository : JpaRepository<IngredientDetail, Long> {

    fun findIngredientDetailsByRefrigerator(refrigerator: Refrigerator): List<IngredientDetail>
}