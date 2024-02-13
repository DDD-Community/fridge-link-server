package mara.server.domain.refrigerator

import mara.server.domain.user.UserRepository
import mara.server.domain.user.UserService
import org.springframework.stereotype.Service

@Service
class RefrigeratorService(
    private val refrigeratorRepository: RefrigeratorRepository,
    private val userRepository: UserRepository,
    private val userService: UserService
) {
    fun createRefrigerator(refrigeratorRequest: RefrigeratorRequest): Long {
        val user = userService.getCurrentLoginUser()
        val refrigerator = Refrigerator(
            name = refrigeratorRequest.name,
            user = user,
            ingredientAddDate = null
        )
        return refrigeratorRepository.save(refrigerator).refrigeratorId
    }

    fun getRefrigerator(id: Long): RefrigeratorResponse {
        val refrigerator =
            refrigeratorRepository.findById(id).orElseThrow { NoSuchElementException("해당 냉장고가 존재하지 않습니다. ID: $id") }
        return RefrigeratorResponse(refrigerator)
    }

    fun getRefrigeratorList(userId: Long): List<RefrigeratorResponse> {
        val user =
            userRepository.findById(userId).orElseThrow { NoSuchElementException("해당 유저가 존재하지 않습니다. ID: $userId") }
        val refrigeratorList = refrigeratorRepository.findRefrigeratorsByUser(user)
        return refrigeratorList.toRefrigeratorResponseList()
    }

    fun getMyRefrigeratorList(): List<RefrigeratorResponse> {
        val user = userService.getCurrentLoginUser()
        val refrigeratorList = refrigeratorRepository.findRefrigeratorsByUser(user)
        return refrigeratorList.toRefrigeratorResponseList()
    }

    fun updateRefrigerator(id: Long, refrigeratorUpdateRequest: RefrigeratorUpdateRequest): RefrigeratorResponse {
        val refrigerator =
            refrigeratorRepository.findById(id).orElseThrow { NoSuchElementException("해당 냉장고가 존재하지 않습니다. ID: $id") }
        refrigerator.update(refrigeratorUpdateRequest)
        return RefrigeratorResponse(refrigeratorRepository.save(refrigerator))
    }

    fun deleteRefrigerator(id: Long): String {
        refrigeratorRepository.deleteById(id)
        return "deleted"
    }
}
