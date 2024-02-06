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
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/ingrs/detail")
class IngredientDetailController(
    private val ingredientDetailService: IngredientDetailService
) {

    @PostMapping
    fun createIngredientDetail(@RequestBody ingredientDetailRequest: IngredientDetailRequest): CommonResponse<Long> {
        return success(ingredientDetailService.createIngredientDetail(ingredientDetailRequest))
    }

//    @GetMapping("/{id}")
//    fun getIngredientDetail(@PathVariable(name = "id") id: Long): CommonResponse<IngredientDetailResponse> {
//        return success(ingredientDetailService.getIngredientDetail(id))
//    }

    @GetMapping("/refrig/{id}")
    fun getIngredientDetailList(@PathVariable(name = "id") refrigeratorId: Long): CommonResponse<List<IngredientDetailResponse>> {
        return success(ingredientDetailService.getIngredientDetailList(refrigeratorId))
    }

    @GetMapping("/count")
    fun getIngredientDetailCount(@RequestParam("day") days: Long): CommonResponse<Long> {
        return success(ingredientDetailService.getIngredientDetailCount(days))
    }

    @GetMapping("/recent")
    fun getIngredientDetailRecent(@RequestParam(name = "count") count: Int): CommonResponse<List<IngredientDetailResponse>> {
        return success(ingredientDetailService.getIngredientDetailRecent(count))
    }

    @PutMapping("/{id}")
    fun updateIngredientDetail(@PathVariable(name = "id") id: Long, @RequestBody ingredientDetailUpdateRequest: IngredientDetailUpdateRequest): CommonResponse<IngredientDetailResponse> {
        return success(ingredientDetailService.updateIngredientDetail(id, ingredientDetailUpdateRequest))
    }

    @DeleteMapping("/{id}")
    fun deleteIngredient(@PathVariable(name = "id") id: Long): CommonResponse<String> {
        return success(ingredientDetailService.deleteIngredientDetail(id))
    }
}
