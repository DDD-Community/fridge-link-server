package mara.server.domain.refrigerator

import jakarta.persistence.*
import mara.server.domain.user.User

@Entity
class Refrigerator(
    var name: String = "기본 냉장고",
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    val user: User,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refrigerator_id", nullable = false)
    var refrigeratorId: Long = 0L
}