package mara.server.domain.ingredient

import mara.server.domain.refrigerator.Refrigerator
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface IngredientDetailRepository : JpaRepository<IngredientDetail, Long> {

    fun findIngredientDetailByIngredientDetailIdAndIsDeletedIsFalse(ingredientDetailId: Long): Optional<IngredientDetail>
    fun findIngredientDetailsByRefrigeratorAndIsDeletedIsFalse(refrigerator: Refrigerator): Optional<List<IngredientDetail>>
}
