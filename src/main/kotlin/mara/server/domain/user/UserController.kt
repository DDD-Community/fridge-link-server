package mara.server.domain.user

import mara.server.common.CommonResponse
import mara.server.common.success
import mara.server.domain.share.ShareResponse
import mara.server.domain.share.ShareService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService,
    private val shareService: ShareService,
) {

    @PostMapping
    fun createUser(@RequestBody userRequest: UserRequest): CommonResponse<JwtDto> {
        return success(userService.singUp(userRequest))
    }

    @GetMapping("/nickname/check")
    fun checkNickname(@RequestParam("nickname") nickname: String): CommonResponse<CheckDuplicateResponse> = success(userService.checkNickName(nickname))

    @GetMapping("/kakao-login")
    fun kakaoLogin(@RequestParam(value = "code") authorizedCode: String): CommonResponse<AuthDto> {
        return success(userService.kakaoLogin(authorizedCode))
    }

    @GetMapping("/google-login")
    fun googleLogin(@RequestParam(value = "code") authorizedCode: String): CommonResponse<AuthDto> {
        return success(userService.googleLogin(authorizedCode))
    }

    @PostMapping("/accessToken")
    fun refreshAccessToken(@RequestBody refreshToken: RefreshAccessTokenRequest): CommonResponse<JwtDto> {
        return success(userService.refreshAccessToken(refreshToken))
    }

    @GetMapping("/me")
    fun getCurrentLoginUser(): CommonResponse<UserResponse> {
        return success(userService.getCurrentUserInfo())
    }

    @GetMapping("/me/invite-code")
    fun getCurrentLoginUserInviteCode(): CommonResponse<UserInviteCodeResponse> {
        return success(userService.getCurrentLoginUserInviteCode())
    }

    @GetMapping("/me/shares")
    fun getAllMyShareList(): CommonResponse<List<ShareResponse>?> {
        return success(shareService.getAllMyShareList())
    }

    @GetMapping("/me/shares/applies")
    fun getAllMyApplyShareList(): CommonResponse<List<ShareResponse>?> {
        return success(shareService.getAllMyApplyShareList())
    }
}
