package mara.server.domain.share

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
import java.time.LocalDateTime

@Entity
data class Share(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    val user: User,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "refrigIngrId")
    val ingredientDetail: IngredientDetail,
    var title: String,
    var content: String,
    var limitDatetime: LocalDateTime,
    var limitPerson: Int,
    var personCnt: Int,
    var location: String,
    var status: String,
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
