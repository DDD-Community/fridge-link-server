package mara.server.domain.refrigerator

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
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
@Tag(name = "냉장고", description = "냉장고 API")
class RefrigeratorController(
    private val refrigeratorService: RefrigeratorService
) {

    @PostMapping
    @Operation(summary = "냉장고 생성 API")
    fun createRefrigerator(@RequestBody refrigeratorRequest: RefrigeratorRequest): CommonResponse<Long> {
        return success(refrigeratorService.createRefrigerator(refrigeratorRequest))
    }

// TODO : FE API 연동 테스트 이후 삭제 예정
//    @GetMapping("/{id}")
//    fun getRefrigerator(@PathVariable(name = "id") id: Long): CommonResponse<RefrigeratorResponse> {
//        return success(refrigeratorService.getRefrigerator((id)))
//    }

    @GetMapping("/users/{user-id}")
    @Operation(summary = "다른 사용자 냉장고 리스트 조회 API")
    fun getRefrigeratorList(@PathVariable(name = "user-id") userId: Long): CommonResponse<List<RefrigeratorResponse>> {
        return success(refrigeratorService.getRefrigeratorList((userId)))
    }

    @GetMapping("/my")
    @Operation(summary = "현 로그인 사용자 냉장고 리스트 조회 API")
    fun getMyRefrigeratorList(): CommonResponse<List<RefrigeratorResponse>> {
        return success(refrigeratorService.getMyRefrigeratorList())
    }

    @PutMapping("/{id}")
    @Operation(summary = "냉장고 업데이트 API")
    fun updateRefrigerator(
        @PathVariable(name = "id") id: Long,
        @RequestBody refrigeratorRequest: RefrigeratorRequest
    ): CommonResponse<RefrigeratorResponse> {
        return success(refrigeratorService.updateRefrigerator(id, refrigeratorRequest))
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "냉장고 삭제 API", description = "냉장고 id와 냉장고 ")
    fun deleteRefrigerator(@PathVariable(name = "id") id: Long): CommonResponse<String> {
        return success(refrigeratorService.deleteRefrigerator(id))
    }
}
