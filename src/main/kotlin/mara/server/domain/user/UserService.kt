package mara.server.domain.user

import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {

    fun createUser(userRequest:UserRequest):Long{
        val user=User(
            name = userRequest.name
        )
        return userRepository.save(user).userId
    }
}