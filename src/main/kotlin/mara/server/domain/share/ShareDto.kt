package mara.server.domain.share

import mara.server.domain.user.ProfileImage
import org.springframework.data.domain.Page
import java.time.LocalDate
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
    val thumbNailImage: String
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
    val nickname: String,
    val profileImage: ProfileImage,
    val shareId: Long,
    val title: String,
    val itemName: String,
    val content: String,
    val shareTime: LocalTime,
    val shareDate: LocalDate,
    // 식재료 소비 기한
    val limitDate: LocalDate,
    val limitPerson: Int,
    val location: String,
    val status: String,
    val thumbNailImage: String
) {
    constructor(share: Share) : this(
        nickname = share.user.nickname,
        profileImage = share.user.profileImage,
        shareId = share.id,
        title = share.title,
        content = share.content,
        shareTime = share.shareTime,
        shareDate = share.shareDate,
        itemName = share.ingredientDetail.ingredient.name,
        limitDate = share.ingredientDetail.expirationDate.toLocalDate(),
        limitPerson = share.limitPerson,
        location = share.location,
        status = share.status.name,
        thumbNailImage = share.thumbNailImage,
    )
}

data class AppliedUserDto(
    val nickname: String,
    val profileImage: ProfileImage,
) {
    constructor(applyShare: ApplyShare) : this(
        nickname = applyShare.user.nickname,
        profileImage = applyShare.user.profileImage,
    )
}

fun Page<Share>.toShareResponseListPage(): Page<ShareResponse> {
    return this.map { ShareResponse(it) }
}

fun List<ApplyShare>.toApplyShareResponseList(): List<AppliedUserDto> {
    return this.map { AppliedUserDto(it) }
}
