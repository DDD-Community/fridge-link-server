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

    fun getIngredient(): Long {
        return 1
    }

    fun updateIngredient(): Long {
        return 1
    }

    fun deleteIngredient(): Long {
        return 1
    }
}