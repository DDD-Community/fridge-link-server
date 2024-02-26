package mara.server.domain.share

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import mara.server.common.BaseEntity
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
    @Enumerated(EnumType.STRING)
    var status: ShareStatus,
    var thumbnailImage: String
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    var peopleCount: Int = 0

    @OneToMany(mappedBy = "share", cascade = [CascadeType.ALL], orphanRemoval = true)
    protected val applyShareMutableList: MutableList<ApplyShare> = mutableListOf()
    val applyShareList: List<ApplyShare> get() = applyShareMutableList.toList()

    fun addApplyShareList(applyShare: ApplyShare) {
        this.applyShareMutableList.add(applyShare)
    }

    fun plusPeopleCount() {
        this.peopleCount += 1
    }

    fun minusPeopleCount() {
        this.peopleCount -= 1
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
        this.thumbnailImage = updateShareRequest.thumbNailImage
    }
}

enum class ShareStatus() {
    SHARE_START,
    SHARE_IN_PROGRESS,
    SHARE_COMPLETE
}
