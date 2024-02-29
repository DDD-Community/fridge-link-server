package mara.server.exception

class ShareException

class IllegalAccessCreatedByLoginUserException(message: String) : RuntimeException(message) {
    companion object {
        const val CREATED_BY_LOGIN_USER = "본인이 올린 나눔 글에는 신청을 할 수 없습니다."
    }
}
