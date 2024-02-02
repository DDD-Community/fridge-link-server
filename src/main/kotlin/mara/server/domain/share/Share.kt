package mara.server.domain.share

import com.fasterxml.jackson.annotation.JsonValue
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import mara.server.domain.ingredient.IngredientDetail
import mara.server.domain.user.User
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Entity
data class Share(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    val user: User,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredientDetailId")
    var ingredientDetail: IngredientDetail,
    var title: String,
    var content: String,
    // 나눔 약속 시간, 날짜
    var shareTime: LocalTime,
    var shareDate: LocalDate,
    var shareDatetime: LocalDateTime,
    var limitPerson: Int,
    var location: String,
    var status: ShareStatus,
    var thumbNailImage: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    var peopleCnt: Int = 0

    @OneToMany(mappedBy = "share", cascade = [CascadeType.ALL], orphanRemoval = true)
    protected val applyShareMutableList: MutableList<ApplyShare> = mutableListOf()
    val applyShareList: List<ApplyShare> get() = applyShareMutableList.toList()

    fun addApplyShareList(applyShare: ApplyShare) {
        this.applyShareMutableList.add(applyShare)
    }

    fun plusPeopleCnt() {
        this.peopleCnt += 1
    }

    fun minusPeopleCnt() {
        this.peopleCnt -= 1
    }
    fun updateIngredientDetail(ingredientDetail: IngredientDetail) {
        this.ingredientDetail = ingredientDetail
    }

    fun updateStatus(status: ShareStatus) {
        this.status = status
    }
    fun updateShare(updateShareRequest: UpdateShareRequest) {
        this.title = updateShareRequest.title
        this.content = updateShareRequest.content
        this.shareDate = updateShareRequest.shareDate
        this.shareTime = updateShareRequest.shareTime
        this.shareDatetime = updateShareRequest.shareDate.atTime(updateShareRequest.shareTime)
        this.limitPerson = updateShareRequest.limitPerson
        this.location = updateShareRequest.location
        this.thumbNailImage = updateShareRequest.thumbNailImage
    }
}

enum class ShareStatus(@JsonValue val statusValue: String) {
    SHARE_START("start"),
    SHARE_IN_PROGRESS("in_progress"),
    SHARE_COMPLETE("complete")
}
