package mara.server.domain.ingredient

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
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
@RequestMapping("/ingrs")
@Tag(name = "식자재", description = "식자재 API")
class IngredientController(
    private val ingredientService: IngredientService
) {

    @PostMapping
    @Operation(summary = "식자재 생성 API")
    fun createIngredient(@RequestBody ingredientRequest: IngredientRequest): CommonResponse<Long> {
        return success(ingredientService.createIngredient(ingredientRequest))
    }

    @GetMapping("/{id}")
    @Operation(summary = "식자재 조회 API")
    fun getIngredient(@PathVariable(name = "id") id: Long): CommonResponse<IngredientResponse> {
        return success(ingredientService.getIngredient(id))
    }

    @GetMapping
    @Operation(summary = "식자재 리스트 조회 API")
    fun getIngredientList(): CommonResponse<List<IngredientResponse>> {
        return success(ingredientService.getIngredientList())
    }

    @GetMapping("/category")
    @Operation(summary = "카테고리별 식자재 리스트 조회 API")
    fun getIngredientGroupListByCategory(): CommonResponse<List<IngredientGroupResponse>> {
        return success(ingredientService.getIngredientListByCategory())
    }

    @PutMapping("/{id}")
    @Operation(summary = "식자재 업데이트 API")
    fun updateIngredient(
        @PathVariable(name = "id") id: Long,
        @RequestBody ingredientRequest: IngredientRequest
    ): CommonResponse<IngredientResponse> {
        return success(ingredientService.updateIngredient(id, ingredientRequest))
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "식자재 삭제 조회 API")
    fun deleteIngredient(@PathVariable(name = "id") id: Long): CommonResponse<String> {
        return success(ingredientService.deleteIngredient(id))
    }
}
