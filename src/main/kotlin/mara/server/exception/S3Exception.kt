package mara.server.exception

class S3Exception

class InvalidS3PathException(message: String) : RuntimeException(message) {
    companion object {
        const val INVALID_S3_PATH = "올바르지 않은 S3 경로입니다."
    }
}
