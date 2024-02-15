package mara.server.domain.ingredient

import mara.server.common.CommonResponse
import mara.server.common.success
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/ingrs/detail")
class IngredientDetailController(
        private val ingredientDetailService: IngredientDetailService
) {

    @PostMapping
    fun createIngredientDetail(@RequestBody ingredientDetailRequest: IngredientDetailRequest): CommonResponse<Long> {
        return success(ingredientDetailService.createIngredientDetail(ingredientDetailRequest))
    }

    @GetMapping("/{id}")
    fun getIngredientDetail(@PathVariable(name = "id") id: Long): CommonResponse<IngredientDetailResponse> {
        return success(ingredientDetailService.getIngredientDetail(id))
    }

    @GetMapping("/refrig/{id}")
    fun getIngredientDetailList(
            @PageableDefault(
                    size = 5
            )
            pageable: Pageable,
            @PathVariable(name = "id") refrigeratorId: Long): CommonResponse<Page<IngredientDetailResponse>> {
        return success(ingredientDetailService.getIngredientDetailList(refrigeratorId, pageable))
    }

    @GetMapping("/count")
    fun getIngredientDetailCount(@RequestParam("day") days: Long): CommonResponse<Long> {
        return success(ingredientDetailService.getIngredientDetailCount(days))
    }

    @GetMapping("/recent")
    fun getIngredientDetailRecent(
            @PageableDefault(
                    size = 4, sort = ["expirationDate"], direction = Sort.Direction.ASC
            )
            pageable: Pageable,
    ): CommonResponse<Page<IngredientDetailResponse>> {
        return success(ingredientDetailService.getIngredientDetailRecent(pageable))
    }

    @PutMapping("/{id}")
    fun updateIngredientDetail(
            @PathVariable(name = "id") id: Long,
            @RequestBody ingredientDetailUpdateRequest: IngredientDetailUpdateRequest
    ): CommonResponse<IngredientDetailResponse> {
        return success(ingredientDetailService.updateIngredientDetail(id, ingredientDetailUpdateRequest))
    }

    @DeleteMapping("/{id}")
    fun deleteIngredient(@PathVariable(name = "id") id: Long): CommonResponse<String> {
        return success(ingredientDetailService.deleteIngredientDetail(id))
    }
}
