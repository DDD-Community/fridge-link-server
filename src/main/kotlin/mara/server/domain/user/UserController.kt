package mara.server.domain.user

import mara.server.common.CommonResponse
import mara.server.common.success
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) {

    @PostMapping
    fun createUser(@RequestBody userRequest: UserRequest): CommonResponse<Long> {
        return success(userService.createUser(userRequest))
    }

    @GetMapping("/kakao-login")
    fun kakaoLogin(@RequestParam(value = "code") authorizedCode: String): JwtDto {
        return userService.kakaoLogin(authorizedCode)
    }

    @GetMapping("/google-login")
    fun googleLogin(@RequestParam(value = "code") authorizedCode: String): JwtDto {
        return userService.googleLogin(authorizedCode)
    }

    @GetMapping("/me")
    fun getCurrentLoginUser(): CommonResponse<UserResponse> {
        return success(userService.getCurrentUserInfo())
    }

    @GetMapping("/me/invite-code")
    fun getCurrentLoginUserInviteCode(): CommonResponse<UserInviteCodeResponse> {
        return success(userService.getCurrentLoginUserInviteCode())
    }
}
