package mara.server.domain.ingredient

import org.springframework.stereotype.Service

@Service
class IngredientService(
        private val ingredientRepository: IngredientRepository
) {

    fun createIngredient(): Long {
        return 1
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