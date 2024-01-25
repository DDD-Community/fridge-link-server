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
    var memo: String,
    var addDate: LocalDateTime,
    var expirationDate: LocalDateTime,
    var isDeleted: Boolean? = null
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val ingredientDetailId: Long = 0L

    fun update(ingredientDetailRequest: IngredientDetailRequest) {
        this.quantity = ingredientDetailRequest.quantity
        this.location = ingredientDetailRequest.location
        this.memo = ingredientDetailRequest.memo
        this.addDate = ingredientDetailRequest.addDate
        this.expirationDate = ingredientDetailRequest.expirationDate
        this.isDeleted = ingredientDetailRequest.isDeleted
    }

    fun delete() {
        this.isDeleted = true
    }
}
