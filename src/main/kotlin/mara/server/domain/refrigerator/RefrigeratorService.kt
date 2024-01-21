package mara.server.domain.refrigerator

import org.springframework.stereotype.Service

@Service
class RefrigeratorService() {

    fun getBoard(id: Long): String {
        return "service ok"
    }
}