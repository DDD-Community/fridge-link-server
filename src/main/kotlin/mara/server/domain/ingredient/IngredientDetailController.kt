package mara.server.domain.ingredient

import mara.server.common.CommonResponse
import mara.server.common.success
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/ingrs/det")
class IngredientDetailController {

    @PostMapping
    fun createIngredientDetail(): CommonResponse<Long> {
        return success(1)
    }

    @GetMapping("/{id}")
    fun getIngredientDetail(@PathVariable(name = "id") id: Long): CommonResponse<Long> {
        return success(2)
    }

    @PutMapping("/{id}")
    fun updateIngredientDetail(@PathVariable(name = "id") id: Long): CommonResponse<Long> {
        return success(3)
    }

    @DeleteMapping("/{id}")
    fun deleteIngredient(@PathVariable(name = "id") id: Long): CommonResponse<Long> {
        return success(4)
    }
}