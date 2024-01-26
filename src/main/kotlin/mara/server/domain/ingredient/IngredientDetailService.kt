package mara.server.domain.ingredient

import mara.server.domain.refrigerator.RefrigeratorRepository
import org.springframework.stereotype.Service

@Service
class IngredientDetailService(
    private val ingredientDetailRepository: IngredientDetailRepository,
    private val refrigeratorRepository: RefrigeratorRepository,
    private val ingredientRepository: IngredientRepository
) {

    fun createIngredientDetail(ingredientDetailRequest: IngredientDetailRequest): Long {
        val refrigeratorId = ingredientDetailRequest.refrigeratorId
        val refrigerator = refrigeratorRepository.findById(refrigeratorId)
            .orElseThrow { NoSuchElementException("해당 냉장고가 존재하지 않습니다. ID: $refrigeratorId") }

        val ingredientId = ingredientDetailRequest.ingredientId
        val ingredient = ingredientRepository.findById(ingredientId)
            .orElseThrow { NoSuchElementException("해당 식재료가 존재하지 않습니다. ID: $ingredientId") }

        val ingredientDetail = IngredientDetail(
            refrigerator = refrigerator,
            ingredient = ingredient,
            quantity = ingredientDetailRequest.quantity,
            location = ingredientDetailRequest.location,
            memo = ingredientDetailRequest.memo,
            addDate = ingredientDetailRequest.addDate,
            expirationDate = ingredientDetailRequest.expirationDate,
            isDeleted = ingredientDetailRequest.isDeleted
        )
        return ingredientDetailRepository.save(ingredientDetail).ingredientDetailId
    }

    fun getIngredientDetail(id: Long): IngredientDetailResponse {
        val ingredientDetail = ingredientDetailRepository.findIngredientDetailByIngredientDetailIdAndIsDeletedIsFalse(id)
            .orElseThrow { NoSuchElementException("해당 식재료 상세가 존재하지 않습니다. ID: $id") }
        return IngredientDetailResponse(ingredientDetail)
    }

    fun getIngredientDetailList(refrigeratorId: Long): List<IngredientDetailResponse> {
        val refrigerator = refrigeratorRepository.findById(refrigeratorId)
            .orElseThrow { NoSuchElementException("해당 냉장고가 존재하지 않습니다. ID: $refrigeratorId") }
        val ingredientDetailList =
            ingredientDetailRepository.findIngredientDetailsByRefrigeratorAndIsDeletedIsFalse(refrigerator)
                .orElseThrow { NoSuchElementException("해당 식재료 상세가 존재하지 않습니다. ID: $refrigeratorId") }
        return ingredientDetailList.toIngredientResponseList()
    }

    fun updateIngredientDetail(
        id: Long,
        ingredientDetailUpdateRequest: IngredientDetailUpdateRequest
    ): IngredientDetailResponse {
        val ingredientDetail = ingredientDetailRepository.findById(id)
            .orElseThrow { NoSuchElementException("해당 식재료 상세가 존재하지 않습니다. ID: $id") }
        ingredientDetail.update(ingredientDetailUpdateRequest)
        return IngredientDetailResponse(ingredientDetailRepository.save(ingredientDetail))
    }

    fun deleteIngredientDetail(id: Long): String {
        val ingredientDetail = ingredientDetailRepository.findById(id)
            .orElseThrow { NoSuchElementException("해당 식재료 상세가 존재하지 않습니다. ID: $id") }
        ingredientDetail.delete()
        ingredientDetailRepository.save(ingredientDetail)
        return "deleted"
    }
}
