package mara.server.domain.refrigerator

import com.querydsl.jpa.impl.JPAQueryFactory
import mara.server.domain.refrigerator.QRefrigerator.refrigerator
import mara.server.domain.user.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RefrigeratorRepository : JpaRepository<Refrigerator, Long> {
    fun findByUser(user: User): List<Refrigerator>
}

interface CustomRefrigeratorRepository {

    fun findByUserList(userList: List<User>, limit: Long): List<Refrigerator>
}

@Repository
class CustomRefrigeratorRepositoryImpl(
    private val query: JPAQueryFactory
) : CustomRefrigeratorRepository {

    override fun findByUserList(userList: List<User>, limit: Long): List<Refrigerator> {
        return query.selectFrom(refrigerator).where(refrigerator.user.`in`(userList)).limit(limit).fetch()
    }
}
