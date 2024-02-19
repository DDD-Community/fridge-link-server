package mara.server.domain.ingredient

import com.querydsl.jpa.impl.JPAQueryFactory
import mara.server.domain.ingredient.QIngredientDetail.ingredientDetail
import mara.server.domain.refrigerator.Refrigerator
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface IngredientDetailRepository : JpaRepository<IngredientDetail, Long>, CustomIngredientDetailRepository

interface CustomIngredientDetailRepository {

    fun findByRefrigerator(
        refrigerator: Refrigerator,
        limit: Long
    ): List<IngredientDetail>

    fun findByRefrigerator(
        refrigerator: Refrigerator,
        pageable: Pageable
    ): Page<IngredientDetail>

    fun findByRefrigeratorList(
        refrigeratorList: List<Refrigerator>,
        limit: Long
    ): List<IngredientDetail>

    fun countByRefrigeratorListAndExpirationDay(
        refrigeratorList: List<Refrigerator>,
        expirationDay: LocalDateTime
    ): Long
}

class CustomIngredientDetailRepositoryImpl(
    private val query: JPAQueryFactory
) : CustomIngredientDetailRepository {

    override fun findByRefrigerator(refrigerator: Refrigerator, limit: Long): List<IngredientDetail> {
        return query.selectFrom(ingredientDetail)
            .where(ingredientDetail.refrigerator.eq(refrigerator).and(ingredientDetail.isDeleted.isFalse)).orderBy(
                ingredientDetail.addDate.desc()
            ).limit(limit)
            .fetch()
    }

    override fun findByRefrigerator(refrigerator: Refrigerator, pageable: Pageable): Page<IngredientDetail> {
        val results = query.selectFrom(ingredientDetail)
            .where(ingredientDetail.refrigerator.eq(refrigerator).and(ingredientDetail.isDeleted.isFalse))
            .offset(pageable.offset).limit(pageable.pageSize.toLong()).fetch()

        val count = query.select(ingredientDetail.count()).from(ingredientDetail)
            .where(ingredientDetail.refrigerator.eq(refrigerator).and(ingredientDetail.isDeleted.isFalse))
            .offset(pageable.offset).limit(pageable.pageSize.toLong()).fetchOne() ?: 0

        return PageImpl(results, pageable, count)
    }

    override fun findByRefrigeratorList(refrigeratorList: List<Refrigerator>, limit: Long): List<IngredientDetail> {
        return query.selectFrom(ingredientDetail)
            .where(ingredientDetail.refrigerator.`in`(refrigeratorList).and(ingredientDetail.isDeleted.isFalse))
            .orderBy(ingredientDetail.expirationDate.desc()).limit(limit).fetch()
    }

    override fun countByRefrigeratorListAndExpirationDay(
        refrigeratorList: List<Refrigerator>,
        expirationDay: LocalDateTime
    ): Long {
        val now = LocalDateTime.now()
        val count = query.select(ingredientDetail.count()).from(ingredientDetail).where(
            ingredientDetail.refrigerator.`in`(refrigeratorList)
                .and(ingredientDetail.expirationDate.between(now, expirationDay))
                .and(ingredientDetail.isDeleted.isFalse)
        ).fetchOne()

        return count ?: 0
    }
}
