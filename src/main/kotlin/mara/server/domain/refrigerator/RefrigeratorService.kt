package mara.server.domain.refrigerator

import org.springframework.stereotype.Service

@Service
class RefrigeratorService(
    private val refrigeratorRepository: RefrigeratorRepository
) {

    fun getBoard(id: Long): String {
        val refrigerator =
            refrigeratorRepository.findById(id).orElseThrow { NoSuchElementException("냉장고가 존재하지 않습니다. ID: $id") }
        return "service ok"
    }
}