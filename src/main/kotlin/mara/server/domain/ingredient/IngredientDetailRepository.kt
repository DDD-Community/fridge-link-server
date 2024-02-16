package mara.server.domain.ingredient

import mara.server.domain.refrigerator.Refrigerator
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface IngredientDetailRepository : JpaRepository<IngredientDetail, Long> {

    fun findByRefrigeratorAndIsDeletedIsFalse(refrigerator: Refrigerator, pageable: Pageable): Page<IngredientDetail>

    @Query("SELECT count(i) from IngredientDetail i where i.refrigerator in (?1) and i.expirationDate between now() and ?2")
    fun findIngredientDetailCountByRefrigeratorAndExpirationDate(refrigerator: List<Refrigerator>, expirationDay: LocalDateTime): Long

    @Query("SELECT i from IngredientDetail i where i.refrigerator in (?1)")
    fun findByRefrigerators(@Param("refrigerators")refrigerators: List<Refrigerator>, pageable: Pageable): Page<IngredientDetail>
}
