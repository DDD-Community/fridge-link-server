package mara.server.domain.user

import mara.server.common.CommonResponse
import mara.server.common.success
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) {

    @PostMapping
    fun createUser(@RequestBody userRequest: UserRequest): CommonResponse<Long> {
        return success(userService.createUser(userRequest))
    }

    @GetMapping("/kakao-login")
    fun login(code: String): JwtDto {
        return userService.kaKaoLogin(code)
    }

    @GetMapping("/google-login")
    fun googleLogin(code: String): JwtDto {
        return userService.googleLogin(code)
    }

    @GetMapping("/me")
    fun getCurrentLoginUser(): CommonResponse<UserResponse> {
        return success(userService.getCurrentUserInfo())
    }
}
