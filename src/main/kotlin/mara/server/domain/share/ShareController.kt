package mara.server.domain.share

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import mara.server.common.CommonResponse
import mara.server.common.success
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
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
@Tag(name = "나눔", description = "나눔 API")
class ShareController(private val shareService: ShareService) {

    @PostMapping
    @Operation(summary = "나눔 생성 API")
    fun createShare(@RequestBody shareRequest: ShareRequest): CommonResponse<Long> {
        return success(shareService.createShare(shareRequest))
    }

    @PostMapping("/applies")
    @Operation(summary = "나눔 신청 API")
    fun applyShare(@RequestBody applyShareRequest: ApplyShareRequest): CommonResponse<Long> {
        return success(shareService.applyShare(applyShareRequest))
    }

    @GetMapping("/{id}")
    @Operation(summary = "나눔 상세 조회 API")
    fun getShareInfo(@PathVariable(name = "id") id: Long): CommonResponse<ShareResponse> {
        return success(shareService.getShareInfo(id))
    }

    @GetMapping
    @Operation(summary = "모든 나눔 조회 API")
    fun getAllShareList(pageable: Pageable, status: String): CommonResponse<Page<ShareResponse>> {
        return success(shareService.getAllShareList(pageable, status))
    }

    @GetMapping("/{id}/applies")
    @Operation(summary = "나눔 신청 사용자 이름 조회 API")
    fun getAllApplyUserList(@PathVariable(name = "id") shareId: Long): CommonResponse<List<String>?> {
        return success(shareService.getAllApplyUserList(shareId))
    }

    @PutMapping("/{id}")
    @Operation(summary = "나눔 업데이트 API")
    fun updateShareInfo(@PathVariable(name = "id") shareId: Long, @RequestBody updateShareRequest: UpdateShareRequest): CommonResponse<Boolean> {
        return success(shareService.updateShareInfo(shareId, updateShareRequest))
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "나눔 상태 변경 API")
    fun changeShareStatus(@PathVariable(name = "id") shareId: Long, updateShareStatusRequest: UpdateShareStatusRequest): CommonResponse<Boolean> {
        return success(shareService.changeShareStatus(shareId, updateShareStatusRequest))
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "나눔 삭제 API")
    fun deleteShare(@PathVariable(name = "id") shareId: Long): CommonResponse<String> {
        return success(shareService.deleteShare(shareId))
    }

    @DeleteMapping("/applies/{id}")
    @Operation(summary = "나눔 신청 취소 API")
    fun deleteApply(@PathVariable(name = "id") applyId: Long): CommonResponse<String> {
        return success(shareService.deleteApplyShare(applyId))
    }
}
