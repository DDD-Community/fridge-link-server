package mara.server.domain.ingredient

import org.springframework.stereotype.Service

@Service
class IngredientDetailService(
    private val ingredientDetailRepository: IngredientDetailRepository
) {

    fun createIngredientDetail(ingredientDetailRequest: IngredientDetailRequest): Long {
        val ingredientDetail = IngredientDetail(
            quantity = ingredientDetailRequest.quantity,
            location = ingredientDetailRequest.location,
            memo = ingredientDetailRequest.memo,
            addDate = ingredientDetailRequest.addDate,
            expirationDate = ingredientDetailRequest.expirationDate,
            isDeleted = ingredientDetailRequest.isDeleted
        )
        return ingredientDetailRepository.save(ingredientDetail).ingredientDetailId
    }

    fun getIngredientDetail(): Long {
        return 2
    }

    fun updateIngredientDetail(): Long {
        return 3
    }

    fun deleteIngredientDetail(): Long {
        return 4
    }
}