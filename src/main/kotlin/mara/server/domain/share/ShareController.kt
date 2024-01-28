package mara.server.domain.share

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
@RequestMapping("/shares")
class ShareController(private val shareService: ShareService) {

    @PostMapping
    fun createShare(@RequestBody shareRequest: ShareRequest): CommonResponse<Long> {
        return success(shareService.createShare(shareRequest))
    }

    @PostMapping("/applies")
    fun applyShare(@RequestBody applyShareRequest: ApplyShareRequest): CommonResponse<Long> {
        return success(shareService.applyShare(applyShareRequest))
    }

    @GetMapping("/{id}")
    fun getShareInfo(@PathVariable(name = "id") id: Long): CommonResponse<ShareResponse> {
        return success(shareService.getShareInfo(id))
    }

    @GetMapping
    fun getAllShareList(): CommonResponse<List<ShareResponse>?> {
        return success(shareService.getAllShareList())
    }

    @GetMapping("/{id}/applies")
    fun getAllApplyUserList(@PathVariable(name = "id") shareId: Long): CommonResponse<List<String>?> {
        return success(shareService.getAllApplyUserList(shareId))
    }

    @PutMapping("/{id}")
    fun updateShareInfo(@PathVariable(name = "id") shareId: Long, @RequestBody updateShareRequest: UpdateShareRequest): CommonResponse<Boolean> {
        return success(shareService.updateShareInfo(shareId, updateShareRequest))
    }

    @PutMapping("/{id}/status")
    fun changeShareStatus(@PathVariable(name = "id") shareId: Long, updateShareStatusRequest: UpdateShareStatusRequest): CommonResponse<Boolean> {
        return success(shareService.changeShareStatus(shareId, updateShareStatusRequest))
    }

    @DeleteMapping("/{id}")
    fun deleteShare(@PathVariable(name = "id") shareId: Long): CommonResponse<String> {
        return success(shareService.deleteShare(shareId))
    }

    @DeleteMapping("/applies/{id}")
    fun deleteApply(@PathVariable(name = "id") applyId: Long): CommonResponse<String> {
        return success(shareService.deleteApply(applyId))
    }
}
