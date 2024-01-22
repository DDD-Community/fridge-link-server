package mara.server.domain.ingredient

import org.springframework.stereotype.Service

@Service
class IngredientService(
    private val ingredientRepository: IngredientRepository
) {

    fun createIngredient(ingredientRequest: IngredientRequest): Long {
        val ingredient = Ingredient(
            category = ingredientRequest.category,
            name = ingredientRequest.name,
            iconImage = ingredientRequest.iconImage,
            expirationDays = ingredientRequest.expirationDays
        )
        return ingredientRepository.save(ingredient).ingredientId
    }

    fun getIngredient(id: Long): IngredientResponse {
        val ingredient = ingredientRepository.findById(id).orElseThrow { NoSuchElementException("해당 식재료가 존재하지 않습니다. ID: $id") }
        return IngredientResponse(ingredient)
    }

    fun updateIngredient(): Long {
        return 1
    }

    fun deleteIngredient(): Long {
        return 1
    }
}