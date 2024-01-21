package mara.server.domain.refrigerator

import mara.server.domain.user.UserRepository
import org.springframework.stereotype.Service

@Service
class RefrigeratorService(
    private val refrigeratorRepository: RefrigeratorRepository,
    private val userRepository: UserRepository
) {
    fun createRefrigerator(refrigeratorRequest: RefrigeratorRequest): Long {
        val userId = refrigeratorRequest.userId
        val user =
            userRepository.findById(userId).orElseThrow { NoSuchElementException("해당 유저가 존재하지 않습니다. ID: $userId") }
        val refrigerator = Refrigerator(
            name = refrigeratorRequest.name,
            user = user
        )
        return refrigeratorRepository.save(refrigerator).refrigeratorId
    }

    fun getRefrigerator(id: Long): RefrigeratorResponse {
        val refrigerator =
            refrigeratorRepository.findById(id).orElseThrow { NoSuchElementException("해당 냉장고가 존재하지 않습니다. ID: $id") }
        return RefrigeratorResponse(refrigerator)
    }

    fun updateRefrigerator(id: Long, refrigeratorRequest: RefrigeratorRequest): RefrigeratorResponse {
        val refrigerator =
            refrigeratorRepository.findById(id).orElseThrow { NoSuchElementException("해당 냉장고가 존재하지 않습니다. ID: $id") }
        refrigerator.update(refrigeratorRequest)
        return RefrigeratorResponse(refrigeratorRepository.save(refrigerator))
    }

    fun deleteRefrigerator(id: Long): String {
        refrigeratorRepository.deleteById(id)
        return "deleted"
    }
}
