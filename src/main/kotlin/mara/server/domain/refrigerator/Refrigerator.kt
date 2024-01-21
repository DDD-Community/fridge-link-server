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
