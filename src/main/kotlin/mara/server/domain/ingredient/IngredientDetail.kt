package mara.server.domain.ingredient

import com.fasterxml.jackson.annotation.JsonValue
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
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
    var name: String,
    var quantity: Int,

    @Enumerated(EnumType.STRING)
    var location: IngredientLocation,
    var memo: String?,
    var addDate: LocalDateTime,
    var expirationDate: LocalDateTime,
    var isDeleted: Boolean = false
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val ingredientDetailId: Long = 0L

    fun update(ingredientDetailUpdateRequest: IngredientDetailUpdateRequest) {
        this.name = ingredientDetailUpdateRequest.name
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

enum class IngredientLocation() {
    FREEZING,
    REFRIGERATION
}
