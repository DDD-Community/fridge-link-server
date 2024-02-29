package mara.server.domain.user

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import mara.server.common.CommonResponse
import mara.server.common.success
import mara.server.domain.share.ShareResponse
import mara.server.domain.share.ShareService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
@Tag(name = "유저", description = "유저 API")
class UserController(
    private val userService: UserService,
    private val shareService: ShareService,
) {

    @PostMapping
    @Operation(summary = "회원 가입 API")
    fun createUser(@RequestBody userRequest: UserRequest): CommonResponse<JwtDto> {
        return success(userService.singUp(userRequest))
    }

    @GetMapping("/nickname/check")
    @Operation(summary = "닉네임 중복 체크 API")
    fun checkNickname(@RequestParam("nickname") nickname: String): CommonResponse<CheckDuplicateResponse> = success(userService.checkNickname(nickname))

    @GetMapping("/kakao-login")
    @Operation(summary = "카카오 로그인 API")
    fun kakaoLogin(@RequestParam(value = "code") authorizedCode: String, @RequestParam(value = "status") status: String): CommonResponse<AuthDto> {
        return success(userService.kakaoLogin(authorizedCode, status))
    }

    @GetMapping("/google-login")
    @Operation(summary = "구글 로그인 API")
    fun googleLogin(@RequestParam(value = "code") authorizedCode: String, @RequestParam(value = "status") status: String): CommonResponse<AuthDto> {
        return success(userService.googleLogin(authorizedCode, status))
    }

    @GetMapping("/me")
    @Operation(summary = "로그인한 유저 조회 API")
    fun getCurrentLoginUser(): CommonResponse<UserResponse> {
        return success(userService.getCurrentUserInfo())
    }

    @PutMapping
    @Operation(summary = "유저 업데이트 API")
    fun updateUser(@RequestBody userUpdateRequest: UserUpdateRequest): CommonResponse<Boolean> {
        return success(userService.updateUser(userUpdateRequest))
    }

    @GetMapping("/me/invite-code")
    @Operation(summary = "유저 초대 코드 조회 API")
    fun getCurrentLoginUserInviteCode(): CommonResponse<UserInviteCodeResponse> {
        return success(userService.getCurrentLoginUserInviteCode())
    }

    @GetMapping("/me/shares/created")
    @Operation(summary = "유저가 올린 나눔 조회 API")
    fun getAllMyCreatedShareList(pageable: Pageable, @RequestParam("status") status: String): CommonResponse<Page<ShareResponse>> {
        return success(shareService.getAllMyCreatedShareList(pageable, status))
    }

    @GetMapping("/me/shares/applied")
    @Operation(summary = "유저가 신청한 나눔 조회 API")
    fun getAllMyAppliedShareList(pageable: Pageable, @RequestParam("status") status: String): CommonResponse<Page<ShareResponse>> {
        return success(shareService.getAllMyAppliedShareList(pageable, status))
    }

    @GetMapping("/me/shares/all")
    @Operation(summary = "유저가 관련된 모든 나눔 조회 API")
    fun getAllMyShareList(pageable: Pageable, @RequestParam("status") status: String): CommonResponse<Page<ShareResponse>> {
        return success(shareService.getAllMyShareList(pageable, status))
    }

    @GetMapping("/me/statistics")
    @Operation(summary = "유저의 식자재 수, 나눔 수, 친구 수 조회 API")
    fun getCountMyStatistics(): CommonResponse<UserStatisticResponse> {
        return success(userService.getCountMyStatistics())
    }
}
