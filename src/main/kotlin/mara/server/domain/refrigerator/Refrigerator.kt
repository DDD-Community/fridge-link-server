package mara.server.domain.refrigerator

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import mara.server.domain.user.User

@Entity
class Refrigerator(
    var name: String,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    val user: User,

    ) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refrigerator_id", nullable = false)
    var refrigeratorId: Long = 0L

    fun update(refrigeratorRequest: RefrigeratorRequest) {
        this.name = refrigeratorRequest.name
    }
}