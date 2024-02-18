package mara.server.domain.ingredient

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import mara.server.common.CommonResponse
import mara.server.common.success
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/ingrs/detail")
@Tag(name = "식자재 상세", description = "식자재 상세 API")
class IngredientDetailController(
    private val ingredientDetailService: IngredientDetailService
) {

    @PostMapping
    @Operation(summary = "식자재 상세 생성 API")
    fun createIngredientDetail(@RequestBody ingredientDetailRequest: IngredientDetailRequest): CommonResponse<Long> {
        return success(ingredientDetailService.createIngredientDetail(ingredientDetailRequest))
    }

    @GetMapping("/{id}")
    @Operation(summary = "식자재 상세 조회 API")
    fun getIngredientDetail(@PathVariable(name = "id") id: Long): CommonResponse<IngredientDetailResponse> {
        return success(ingredientDetailService.getIngredientDetail(id))
    }

    @GetMapping("/refrig/{id}")
    @Operation(summary = "특정 냉장고 식자재 상세 리스트 조회 API")
    fun getIngredientDetailList(
        @PageableDefault(
            size = 5
        )
        pageable: Pageable,
        @PathVariable(name = "id") refrigeratorId: Long
    ): CommonResponse<Page<IngredientDetailResponse>> {
        return success(ingredientDetailService.getIngredientDetailList(refrigeratorId, pageable))
    }

    @GetMapping("/count")
    @Operation(summary = "소비기한내 식자재 상세 수 조회 API")
    fun getIngredientDetailCount(@RequestParam("day") days: Long): CommonResponse<Long> {
        return success(ingredientDetailService.getIngredientDetailCount(days))
    }

    @GetMapping("/recent")
    @Operation(summary = "소비기한 만료일 기준 정렬 식자재 상세 조회 API")
    fun getIngredientDetailRecent(): CommonResponse<List<IngredientDetailResponse>> {
        return success(ingredientDetailService.getIngredientDetailRecent())
    }

    @PutMapping("/{id}")
    @Operation(summary = "식자재 상세 업데이트 API")
    fun updateIngredientDetail(
        @PathVariable(name = "id") id: Long,
        @RequestBody ingredientDetailUpdateRequest: IngredientDetailUpdateRequest
    ): CommonResponse<IngredientDetailResponse> {
        return success(ingredientDetailService.updateIngredientDetail(id, ingredientDetailUpdateRequest))
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "식자재 상세 삭제 API")
    fun deleteIngredient(@PathVariable(name = "id") id: Long): CommonResponse<String> {
        return success(ingredientDetailService.deleteIngredientDetail(id))
    }
}
