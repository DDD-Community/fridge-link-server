package mara.server.domain.refrigerator

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import mara.server.domain.user.User
import java.time.LocalDateTime

@Entity
class Refrigerator(
    @Column(length = 30)
    var name: String,
    var ingredientAddDate: LocalDateTime?,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    val user: User,

) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refrigerator_id", nullable = false)
    val refrigeratorId: Long = 0L

    fun update(refrigeratorRequest: RefrigeratorRequest) {
        this.name = refrigeratorRequest.name
    }
}
