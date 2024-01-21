package mara.server.domain.refrigerator

import mara.server.common.CommonResponse
import mara.server.common.success
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/refrigs")
class RefrigeratorController(
    private val refrigeratorService: RefrigeratorService
) {
    @PostMapping
    fun createRefrigerator(@RequestBody refrigeratorRequest: RefrigeratorRequest): Long {
        return refrigeratorService.createRefrigerator(refrigeratorRequest)
    }

    @GetMapping("/{id}")
    fun getRefrigerator(@PathVariable(name = "id") id: Long): String {
        return refrigeratorService.getRefrigerator((id))
    }

    @PutMapping("/{id}")
    fun updateRefrigerator(@PathVariable(name = "id") id: Long, @RequestBody refrigeratorRequest: RefrigeratorRequest): CommonResponse<RefrigeratorResponse> {
        return success(refrigeratorService.updateRefrigerator(id, refrigeratorRequest))
    }

    @DeleteMapping("/{id}")
    fun updateRefrigerator(@PathVariable(name = "id") id: Long): CommonResponse<String> {
        return success(refrigeratorService.deleteRefrigerator(id))
    }
}