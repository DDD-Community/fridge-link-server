package mara.server.domain.ingredient

import mara.server.domain.refrigerator.Refrigerator
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.Optional

@Repository
interface IngredientDetailRepository : JpaRepository<IngredientDetail, Long> {

    fun findIngredientDetailByIngredientDetailIdAndIsDeletedIsFalse(ingredientDetailId: Long): Optional<IngredientDetail>
    fun findIngredientDetailsByRefrigeratorAndIsDeletedIsFalse(refrigerator: Refrigerator): Optional<List<IngredientDetail>>

    @Query("SELECT count(i) from IngredientDetail i where i.refrigerator in (?1) and i.expirationDate between now() and ?2")
    fun findIngredientDetailCountByRefrigeratorAndExpirationDate(refrigerator: List<Refrigerator>, expirationDay: LocalDateTime): Long
}
