package mara.server.domain.share

import com.fasterxml.jackson.annotation.JsonValue
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
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
    @JoinColumn(name = "refrigIngrId")
    var ingredientDetail: IngredientDetail,
    var title: String,
    var content: String,
    var limitTime: LocalTime,
    var limitDate: LocalDate,
    var limitDatetime: LocalDateTime,
    var limitPerson: Int,
    var personCnt: Int,
    var location: String,
    var status: ShareStatus,
    var thumbNailImage: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    @OneToMany(mappedBy = "share", cascade = [CascadeType.ALL], orphanRemoval = true)
    protected val applyShareMutableList: MutableList<ApplyShare> = mutableListOf()
    val applyShareList: List<ApplyShare> get() = applyShareMutableList.toList()

    fun addApplyShareList(applyShare: ApplyShare) {
        this.applyShareMutableList.add(applyShare)
    }

    fun updateIngredientDetail(ingredientDetail: IngredientDetail) {
        this.ingredientDetail = ingredientDetail
    }

    fun updateStatus(status: ShareStatus) {
        this.status = status
    }
    fun updateShare(updateShareRequest: UpdateShareRequest) {
        this.title = updateShareRequest.title ?: this.title
        this.content = updateShareRequest.content ?: this.content
        this.limitDate = updateShareRequest.limitDate ?: this.limitDate
        this.limitTime = updateShareRequest.limitTime ?: this.limitTime
        this.limitPerson = updateShareRequest.limitPerson ?: this.limitPerson
        this.personCnt = updateShareRequest.personCnt ?: this.personCnt
        this.location = updateShareRequest.location ?: this.location
        this.thumbNailImage = updateShareRequest.thumbNailImage ?: this.thumbNailImage
    }
}

enum class ShareStatus(@JsonValue val statusValue: String) {
    SHARE_START("start"),
    SHARE_IN_PROGRESS("in_progress"),
    SHARE_COMPLETE("complete")
}

@Entity
data class ApplyShare(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apply_user_id")
    val user: User,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shareId")
    val share: Share,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "apply_share_id")
    val id: Long = 0
}
