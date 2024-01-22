package mara.server.domain.ingredient

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Ingredient(
        var category: String? = null,
        var name: String? = null,
        var iconImage: String? = null,
        var expirationDays: Int = 0
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var ingredientId: Long = 0L

    fun update(ingredientRequest: IngredientRequest) {
        this.category = ingredientRequest.category
        this.name = ingredientRequest.name
        this.iconImage = ingredientRequest.iconImage
        this.expirationDays = ingredientRequest.expirationDays
    }
}
