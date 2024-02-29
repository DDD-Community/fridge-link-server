package mara.server.exception

class UserException() {
    companion object {
        const val NO_SUCH_USER = "해당 유저가 존재하지 않습니다"
    }
}

class InvalidDeployStatusException(message: String) : RuntimeException(message) {
    companion object {
        const val INVALID_DEPLOY_STATUS = "유효하지 않은 배포 상태 값 입니다."
    }
}
