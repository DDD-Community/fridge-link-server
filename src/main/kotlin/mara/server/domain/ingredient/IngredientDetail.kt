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
}