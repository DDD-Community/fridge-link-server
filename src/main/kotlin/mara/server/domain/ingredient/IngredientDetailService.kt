package mara.server.domain.ingredient

import mara.server.domain.refrigerator.RefrigeratorRepository
import mara.server.domain.refrigerator.RefrigeratorService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class IngredientDetailService(
    private val refrigeratorService: RefrigeratorService,
    private val refrigeratorRepository: RefrigeratorRepository,
    private val ingredientRepository: IngredientRepository,
    private val ingredientDetailRepository: IngredientDetailRepository

) {
    private val deleted = "deleted"

    @Transactional
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
            name = ingredientDetailRequest.name,
            quantity = ingredientDetailRequest.quantity,
            location = ingredientDetailRequest.location,
            memo = ingredientDetailRequest.memo,
            addDate = ingredientDetailRequest.addDate,
            expirationDate = ingredientDetailRequest.expirationDate,
            isDeleted = ingredientDetailRequest.isDeleted
        )

        // 식재료 추가 일자 update
        refrigerator.ingredientAddDate = LocalDateTime.now()
        refrigeratorRepository.save(refrigerator)

        return ingredientDetailRepository.save(ingredientDetail).ingredientDetailId
    }

    fun getIngredientDetail(id: Long): IngredientDetailResponse {
        val ingredientDetail = ingredientDetailRepository.findById(id)
            .orElseThrow { NoSuchElementException("해당 식재료 상세가 존재하지 않습니다. ID: $id") }
        return IngredientDetailResponse(ingredientDetail)
    }

    fun getIngredientDetailList(refrigeratorId: Long, location: IngredientLocation, pageable: Pageable): Page<IngredientDetailResponse> {
        val refrigerator = refrigeratorRepository.findById(refrigeratorId)
            .orElseThrow { NoSuchElementException("해당 냉장고가 존재하지 않습니다. ID: $refrigeratorId") }
        val ingredientDetailList =
            ingredientDetailRepository.findByRefrigerator(refrigerator, location, pageable)
        return ingredientDetailList.toIngredientDetailResponseListPage()
    }

    fun getIngredientDetailCount(days: Long): Long {
        val refrigeratorList = refrigeratorService.getCurrentLoginUserRefrigeratorList()
        val expirationDate = LocalDateTime.now().plusDays(days)

        return ingredientDetailRepository.countByRefrigeratorListAndExpirationDay(
            refrigeratorList,
            expirationDate
        )
    }

    fun getIngredientDetailRecent(): List<IngredientDetailResponse> {
        val refrigeratorList = refrigeratorService.getCurrentLoginUserRefrigeratorList()
        val ingredientDetailRecentList =
            ingredientDetailRepository.findByRefrigeratorList(refrigeratorList, 4)

        return ingredientDetailRecentList.toIngredientDetailResponseList()
    }

    @Transactional
    fun updateIngredientDetail(
        id: Long,
        ingredientDetailUpdateRequest: IngredientDetailUpdateRequest
    ): IngredientDetailResponse {
        val ingredientDetail = ingredientDetailRepository.findById(id)
            .orElseThrow { NoSuchElementException("해당 식재료 상세가 존재하지 않습니다. ID: $id") }
        ingredientDetail.update(ingredientDetailUpdateRequest)
        return IngredientDetailResponse(ingredientDetailRepository.save(ingredientDetail))
    }

    @Transactional
    fun deleteIngredientDetail(id: Long): String {
        val ingredientDetail = ingredientDetailRepository.findById(id)
            .orElseThrow { NoSuchElementException("해당 식재료 상세가 존재하지 않습니다. ID: $id") }
        ingredientDetail.delete()
        ingredientDetailRepository.save(ingredientDetail)
        return deleted
    }
}
