package mara.server.domain.ingredient

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import mara.server.domain.refrigerator.Refrigerator
import java.time.LocalDateTime

@Entity
class IngredientDetail(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "refrigeratorId")
    val refrigerator: Refrigerator,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredientId")
    val ingredient: Ingredient,
    var quantity: Int = 0,
    var location: String,
    var memo: String?,
    var addDate: LocalDateTime,
    var expirationDate: LocalDateTime,
    var isDeleted: Boolean
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val ingredientDetailId: Long = 0L

    fun update(ingredientDetailUpdateRequest: IngredientDetailUpdateRequest) {
        this.quantity = ingredientDetailUpdateRequest.quantity
        this.location = ingredientDetailUpdateRequest.location
        this.memo = ingredientDetailUpdateRequest.memo
        this.addDate = ingredientDetailUpdateRequest.addDate
        this.expirationDate = ingredientDetailUpdateRequest.expirationDate
        this.isDeleted = ingredientDetailUpdateRequest.isDeleted
    }

    fun delete() {
        this.isDeleted = true
    }
}
