package mara.server.domain.refrigerator

import com.querydsl.jpa.impl.JPAQueryFactory
import mara.server.domain.refrigerator.QRefrigerator.refrigerator
import mara.server.domain.user.User
import org.springframework.data.jpa.repository.JpaRepository

interface RefrigeratorRepository : JpaRepository<Refrigerator, Long>, CustomRefrigeratorRepository {
    fun findByUser(user: User): List<Refrigerator>
}

interface CustomRefrigeratorRepository {

    fun findByUserList(userList: List<User>, limit: Long): List<Refrigerator>
}

class CustomRefrigeratorRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : CustomRefrigeratorRepository {

    override fun findByUserList(userList: List<User>, limit: Long): List<Refrigerator> {
        return queryFactory.selectFrom(refrigerator).where(refrigerator.user.`in`(userList)).orderBy(refrigerator.ingredientAddDate.desc()).limit(limit).fetch()
    }
}
