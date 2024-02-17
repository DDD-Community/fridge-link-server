package mara.server.domain.ingredient

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import mara.server.common.BaseEntity

@Entity
class Ingredient(
    var category: String,
    var name: String,
    var iconImage: String,
    var expirationDays: Int = 0
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val ingredientId: Long = 0L

    fun update(ingredientRequest: IngredientRequest) {
        this.category = ingredientRequest.category
        this.name = ingredientRequest.name
        this.iconImage = ingredientRequest.iconImage
        this.expirationDays = ingredientRequest.expirationDays
    }
}
