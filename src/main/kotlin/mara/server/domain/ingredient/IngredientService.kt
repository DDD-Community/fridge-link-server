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

    fun getIngredientList(): List<IngredientResponse> {
        val ingredientList = ingredientRepository.findAll()
        return ingredientList.toIngredientResponseList()
    }

    fun updateIngredient(id: Long, ingredientRequest: IngredientRequest): IngredientResponse {
        val ingredient = ingredientRepository.findById(id).orElseThrow { NoSuchElementException("해당 식재료가 존재하지 않습니다. ID: $id") }
        ingredient.update(ingredientRequest)
        return IngredientResponse(ingredientRepository.save(ingredient))
    }

    fun deleteIngredient(id: Long): String {
        ingredientRepository.deleteById(id)
        return "deleted"
    }
}