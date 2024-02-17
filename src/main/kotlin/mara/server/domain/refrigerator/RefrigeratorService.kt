package mara.server.domain.refrigerator

import mara.server.domain.user.UserRepository
import mara.server.domain.user.UserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RefrigeratorService(
    private val refrigeratorRepository: RefrigeratorRepository,
    private val userService: UserService,
    private val userRepository: UserRepository
) {
    private val deleted = "deleted"

    fun getCurrentLoginUserRefrigeratorList(): List<Refrigerator> = refrigeratorRepository.findByUser(userService.getCurrentLoginUser())

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
//            refrigeratorRepository.findById(id).orElseThrow { NoSuchElementException("해당 냉장고가 존재하지 않습니다. ID: $id") }
//        return RefrigeratorResponse(refrigerator)
//    }

    fun getRefrigeratorList(userId: Long): List<RefrigeratorResponse> {
        val user = userRepository.findById(userId).orElseThrow { NoSuchElementException("해당 유저가 존재하지 않습니다. ID: $userId") }
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
            refrigeratorRepository.findById(id).orElseThrow { NoSuchElementException("해당 냉장고가 존재하지 않습니다. ID: $id") }
        refrigerator.update(refrigeratorRequest)
        return RefrigeratorResponse(refrigeratorRepository.save(refrigerator))
    }

    @Transactional
    fun deleteRefrigerator(id: Long): String {
        refrigeratorRepository.deleteById(id)
        return deleted
    }
}
