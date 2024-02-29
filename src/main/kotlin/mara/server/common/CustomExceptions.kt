package mara.server.common

class CustomExceptions
class InvalidDeployStatusException(message: String) : RuntimeException(message) {
    companion object {
        const val INVALID_DEPLOY_STATUS_ERROR = "유효하지 않은 배포 상태 값 입니다."
    }
}
