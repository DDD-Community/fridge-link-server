package mara.server.domain.ingredient

import mara.server.common.CommonResponse
import mara.server.common.success
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
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

    @GetMapping
    fun getIngredient(): CommonResponse<Long> {
        return success(ingredientService.getIngredient())
    }

    @PutMapping
    fun updateIngredient(): CommonResponse<Long> {
        return success(ingredientService.updateIngredient())
    }

    @DeleteMapping
    fun deleteIngredient(): CommonResponse<Long> {
        return success(ingredientService.deleteIngredient())
    }
}