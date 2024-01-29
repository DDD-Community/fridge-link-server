package mara.server.domain.share

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

data class ShareRequest(
    val title: String,
    val refrigIngrId: Long,
    val content: String,
    val limitDate: LocalDate,
    val limitTime: LocalTime,
    val limitPerson: Int,
    val personCnt: Int,
    val location: String,
    val status: String,
    val thumbNailImage: String,
)

data class ApplyShareRequest(
    val shareId: Long,
)

data class UpdateShareRequest(
    val title: String?,
    val refrigIngrId: Long?,
    val content: String?,
    val limitDate: LocalDate?,
    val limitTime: LocalTime?,
    val limitPerson: Int?,
    val personCnt: Int?,
    val location: String?,
    val thumbNailImage: String?,
)

data class UpdateShareStatusRequest(
    val status: String,
)

data class ShareResponse(
    val shareId: Long,
    val title: String,
    val content: String,
    val limitDatetime: LocalDateTime,
    val limitPerson: Int,
    val personCnt: Int,
    val location: String,
    val status: String,
    val thumbNailImage: String
) {
    constructor(share: Share) : this(
        shareId = share.id,
        title = share.title,
        content = share.content,
        limitDatetime = share.limitDatetime,
        limitPerson = share.limitPerson,
        personCnt = share.personCnt,
        location = share.location,
        status = share.status.statusValue,
        thumbNailImage = share.thumbNailImage,
    )
}

fun List<Share>.toShareResponseList(): List<ShareResponse> {
    return this.map { ShareResponse(it) }
}
