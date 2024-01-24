package mara.server.domain.ingredient

import mara.server.common.CommonResponse
import mara.server.common.success
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/ingrs/det")
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

    // TODO CommonResponse<List<IngredientDetailResponse>> 로 return 변경
    @GetMapping("{refrig-id}")
    fun getIngredientDetailList(@PathVariable(name = "refrig-id") refrigId: Long): CommonResponse<Long> {
        return success(ingredientDetailService.getIngredientDetailList(refrigId))
    }

    @PutMapping("/{id}")
    fun updateIngredientDetail(@PathVariable(name = "id") id: Long, @RequestBody ingredientDetailRequest: IngredientDetailRequest): CommonResponse<IngredientDetailResponse> {
        return success(ingredientDetailService.updateIngredientDetail(id, ingredientDetailRequest))
    }

    @DeleteMapping("/{id}")
    fun deleteIngredient(@PathVariable(name = "id") id: Long): CommonResponse<String> {
        return success(ingredientDetailService.deleteIngredientDetail(id))
    }
}