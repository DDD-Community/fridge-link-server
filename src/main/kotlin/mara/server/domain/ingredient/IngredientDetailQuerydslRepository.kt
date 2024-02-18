package mara.server.domain.ingredient

import com.querydsl.jpa.impl.JPAQueryFactory
import mara.server.domain.refrigerator.Refrigerator
import org.springframework.stereotype.Repository

@Repository
class IngredientDetailQuerydslRepository(
    private val query: JPAQueryFactory
) {
    fun getIngredientDetailByRefrigerator(
        refrigerator: Refrigerator,
        limit: Long
    ): List<IngredientDetail> {
        val ingredientDetail = QIngredientDetail("ingredientDetail")
        return query.selectFrom(ingredientDetail)
            .where(ingredientDetail.refrigerator.eq(refrigerator).and(ingredientDetail.isDeleted.isFalse)).limit(limit)
            .fetch()
    }

    fun getIngredientDetailByRefrigeratorList(
        refrigeratorList: List<Refrigerator>,
        limit: Long
    ): List<IngredientDetail> {
        val ingredientDetail = QIngredientDetail("ingredientDetail")
        return query.selectFrom(ingredientDetail).where(ingredientDetail.refrigerator.`in`(refrigeratorList).and(ingredientDetail.isDeleted.isFalse)).orderBy(ingredientDetail.expirationDate.desc()).limit(limit).fetch()
    }
}
