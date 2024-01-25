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
@RequestMapping("/ingrs")
class IngredientController(
    private val ingredientService: IngredientService
) {

    @PostMapping
    fun createIngredient(@RequestBody ingredientRequest: IngredientRequest): CommonResponse<Long> {
        return success(ingredientService.createIngredient(ingredientRequest))
    }

    @GetMapping("/{id}")
    fun getIngredient(@PathVariable(name = "id") id: Long): CommonResponse<IngredientResponse> {
        return success(ingredientService.getIngredient(id))
    }

    @GetMapping
    fun getIngredientList(): CommonResponse<List<IngredientResponse>> {
        return success(ingredientService.getIngredientList())
    }

    @PutMapping("/{id}")
    fun updateIngredient(
        @PathVariable(name = "id") id: Long,
        @RequestBody ingredientRequest: IngredientRequest
    ): CommonResponse<IngredientResponse> {
        return success(ingredientService.updateIngredient(id, ingredientRequest))
    }

    @DeleteMapping("/{id}")
    fun deleteIngredient(@PathVariable(name = "id") id: Long): CommonResponse<String> {
        return success(ingredientService.deleteIngredient(id))
    }
}
