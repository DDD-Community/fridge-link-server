package mara.server.domain.refrigerator

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/refrigs")
class RefrigeratorController() {

    @GetMapping
    fun getRefrigerator(): String {
        return "ok"
    }
}