package mara.server.domain.user

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import mara.server.common.BaseEntity

enum class Role { USER }
@Entity
@Table(name = "app_user")
class User(
    val nickName: String,
    val password: String,
    val kakaoId: Long?,
    val kakaoEmail: String?,
    val googleEmail: String?,
    val inviteCode: String,
    @Enumerated(EnumType.STRING)
    var profileImage: ProfileImage,
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val userId: Long = 0L

    @Enumerated(EnumType.STRING)
    var role: Role = Role.USER
        protected set
}

enum class ProfileImage {
    BLUE,
    RED,
    YELLOW,
    GREEN
}
