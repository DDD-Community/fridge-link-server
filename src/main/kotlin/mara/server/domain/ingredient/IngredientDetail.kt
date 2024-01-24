package mara.server.domain.ingredient

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDateTime

@Entity
class IngredientDetail(
    var quantity: Int = 0,
    var location: String,
    var memo: String,
    var addDate: LocalDateTime,
    var expirationDate: LocalDateTime,
    var isDeleted: Boolean? = null
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var ingredientDetailId: Long = 0L

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