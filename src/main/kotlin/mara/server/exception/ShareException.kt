package mara.server.exception

class ShareException() {
    companion object {
        const val NO_SUCH_INGREDIENT = "해당 식재료가 존재하지 않습니다"
        const val NO_SUCH_SHARE = "해당 나눔 게시물이 존재하지 않습니다"
        const val NO_SUCH_APPLY_SHARE = "해당 나눔 신청이 존재하지 않습니다"
    }
}

class IllegalAccessShareException(message: String) : RuntimeException(message) {
    companion object {
        const val CREATED_BY_LOGIN_USER = "본인이 올린 나눔 글에는 신청을 할 수 없습니다."
        const val DUPLICATED_APPLY = "이미 신청한 나눔 입니다."
        const val DIFFERENT_USER = "잘못된 사용자로부터 전달된 요청입니다."
    }
}
