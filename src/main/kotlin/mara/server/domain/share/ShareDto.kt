package mara.server.domain.share

import mara.server.domain.user.ProfileImage
import org.springframework.data.domain.Page
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

data class ShareRequest(
    val title: String,
    val ingredientDetailId: Long,
    val content: String,
    val shareTime: LocalTime,
    val shareDate: LocalDate,
    val limitPerson: Int,
    val location: String,
    val status: String,
    val thumbnailImage: String
)

data class ApplyShareRequest(
    val shareId: Long,
)

data class UpdateShareRequest(
    val title: String,
    val ingredientDetailId: Long,
    val content: String,
    val shareDate: LocalDate,
    val shareTime: LocalTime,
    val limitPerson: Int,
    val location: String,
    val thumbNailImage: String,
)

data class UpdateShareStatusRequest(
    val status: String,
)

data class ShareResponse(
    val shareId: Long,
    val title: String,
    val shareTime: LocalTime,
    val shareDate: LocalDate,
    // 식재료 소비 기한
    val limitPerson: Int,
    val location: String,
    val peopleCount: Int,
    val status: String,
    val thumbnailImage: String,
    val isApplied: Boolean?
) {
    constructor(share: Share, isApplied: Boolean?) : this(
        shareId = share.id,
        title = share.title,
        shareTime = share.shareTime,
        shareDate = share.shareDate,
        limitPerson = share.limitPerson,
        location = share.location,
        peopleCount = share.peopleCount,
        status = share.status.name,
        thumbnailImage = share.thumbnailImage,
        isApplied = isApplied
    )
}

data class ShareDetailResponse(
    val nickname: String,
    val profileImage: ProfileImage,
    val title: String,
    val shareTime: LocalTime,
    val shareDate: LocalDate,
    val content: String,
    // 식재료 소비 기한
    val expirationDate: LocalDateTime,
    val limitPerson: Int,
    val location: String,
    val peopleCount: Int,
    val status: ShareStatus,
    val thumbNailImage: String,
    val itemName: String,
    val shareId: Long,
    val isCreatedCurrentLoginUser: Boolean,
) {
    constructor(share: Share, isCreatedCurrentLoginUser: Boolean) : this(
        nickname = share.user.nickname,
        profileImage = share.user.profileImage,
        title = share.title,
        shareTime = share.shareTime,
        shareDate = share.shareDate,
        content = share.content,
        expirationDate = share.ingredientDetail.expirationDate,
        limitPerson = share.limitPerson,
        location = share.location,
        peopleCount = share.peopleCount,
        status = share.status,
        thumbNailImage = share.thumbnailImage,
        itemName = share.ingredientDetail.name,
        shareId = share.id,
        isCreatedCurrentLoginUser = isCreatedCurrentLoginUser
    )
}

data class AppliedUserResponse(
    val nickname: String,
    val profileImage: ProfileImage,
) {
    constructor(applyShare: ApplyShare) : this(
        nickname = applyShare.user.nickname,
        profileImage = applyShare.user.profileImage,
    )
}

fun Page<Share>.toShareResponseListPage(): Page<ShareResponse> {
    return this.map { ShareResponse(it, true) }
}

fun List<ApplyShare>.toApplyShareResponseList(): List<AppliedUserResponse> {
    return this.map { AppliedUserResponse(it) }
}
