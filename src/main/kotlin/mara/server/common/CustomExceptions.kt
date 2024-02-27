package mara.server.common

const val WRONG_STATUS_ERROR = "wrong status value"
class InvalidDeployStatusException(message: String) : RuntimeException(message)
