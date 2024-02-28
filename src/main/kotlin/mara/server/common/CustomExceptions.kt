package mara.server.common

const val WRONG_STATUS_ERROR = "Invalid status value"
class InvalidDeployStatusException(message: String, val additionalData: Any?) : RuntimeException(message)
