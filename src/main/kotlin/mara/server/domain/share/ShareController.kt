package mara.server.domain.share

import mara.server.common.CommonResponse
import mara.server.common.success
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/shares")
class ShareController(private val shareService: ShareService) {

    @PostMapping
    fun createShare(@RequestBody shareRequest: ShareRequest): CommonResponse<Long> {
        return success(shareService.createShare(shareRequest))
    }
}
