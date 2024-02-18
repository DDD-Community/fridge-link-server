package mara.server.domain.refrigerator

import com.querydsl.jpa.impl.JPAQueryFactory
import mara.server.domain.user.User
import org.springframework.stereotype.Repository

@Repository
class RefrigeratorQuerydslRepository(
    private val query: JPAQueryFactory
) {

    fun getRefrigeratorList(userList: List<User>, limit: Long): List<Refrigerator> {
        val refrigerator = QRefrigerator("refrigerator")
        return query.selectFrom(refrigerator).where(refrigerator.user.`in`(userList)).limit(limit).fetch()
    }
}
