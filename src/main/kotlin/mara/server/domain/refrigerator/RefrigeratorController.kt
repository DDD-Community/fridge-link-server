package mara.server.domain.refrigerator

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
@RequestMapping("/refrigs")
class RefrigeratorController(
    private val refrigeratorService: RefrigeratorService
) {
    @PostMapping
    fun createRefrigerator(@RequestBody refrigeratorRequest: RefrigeratorRequest): CommonResponse<Long> {
        return success(refrigeratorService.createRefrigerator(refrigeratorRequest))
    }

    @GetMapping("/{id}")
    fun getRefrigerator(@PathVariable(name = "id") id: Long): CommonResponse<RefrigeratorResponse> {
        return success(refrigeratorService.getRefrigerator((id)))
    }

    @PutMapping("/{id}")
    fun updateRefrigerator(
        @PathVariable(name = "id") id: Long,
        @RequestBody refrigeratorRequest: RefrigeratorRequest
    ): CommonResponse<RefrigeratorResponse> {
        return success(refrigeratorService.updateRefrigerator(id, refrigeratorRequest))
    }

    @DeleteMapping("/{id}")
    fun updateRefrigerator(@PathVariable(name = "id") id: Long): CommonResponse<String> {
        return success(refrigeratorService.deleteRefrigerator(id))
    }
}
