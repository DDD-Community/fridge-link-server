package mara.server.domain.refrigerator

import mara.server.domain.user.UserRepository
import mara.server.domain.user.UserService
import mara.server.exception.RefrigeratorException.Companion.NO_SUCH_REFRIGERATOR
import mara.server.exception.UserException.Companion.NO_SUCH_USER
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RefrigeratorService(
    private val refrigeratorRepository: RefrigeratorRepository,
    private val userService: UserService,
    private val userRepository: UserRepository
) {
    private val deleted = "deleted"

    fun getCurrentLoginUserRefrigeratorList(): List<Refrigerator> =
        refrigeratorRepository.findByUser(userService.getCurrentLoginUser())

    @Transactional
    fun createRefrigerator(refrigeratorRequest: RefrigeratorRequest): Long {
        val user = userService.getCurrentLoginUser()
        val refrigerator = Refrigerator(
            name = refrigeratorRequest.name,
            user = user,
            ingredientAddDate = null
        )
        return refrigeratorRepository.save(refrigerator).refrigeratorId
    }

// TODO : FE API 연동 테스트 이후 삭제 예정
//    fun getRefrigerator(id: Long): RefrigeratorResponse {
//        val refrigerator =
//            refrigeratorRepository.findById(id).orElseThrow { NO_SUCH_REFRIGERATOR }
//        return RefrigeratorResponse(refrigerator)
//    }

    fun getRefrigeratorList(userId: Long): List<RefrigeratorResponse> {
        val user = userRepository.findById(userId).orElseThrow { NoSuchElementException(NO_SUCH_USER) }
        val refrigeratorList = refrigeratorRepository.findByUser(user)
        return refrigeratorList.toRefrigeratorResponseList()
    }

    fun getMyRefrigeratorList(): List<RefrigeratorResponse> {
        val refrigeratorList = getCurrentLoginUserRefrigeratorList()
        return refrigeratorList.toRefrigeratorResponseList()
    }

    @Transactional
    fun updateRefrigerator(id: Long, refrigeratorRequest: RefrigeratorRequest): RefrigeratorResponse {
        val refrigerator =
            refrigeratorRepository.findById(id).orElseThrow { NoSuchElementException(NO_SUCH_REFRIGERATOR) }
        refrigerator.update(refrigeratorRequest)
        return RefrigeratorResponse(refrigeratorRepository.save(refrigerator))
    }

    @Transactional
    fun deleteRefrigerator(id: Long): String {
        refrigeratorRepository.deleteById(id)
        return deleted
    }
}
