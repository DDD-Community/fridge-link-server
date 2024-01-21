package mara.server.domain.refrigerator

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
}