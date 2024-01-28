package mara.server.domain.friend

import mara.server.domain.user.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface FriendshipRepository : JpaRepository<Friendship, Long> {

    @Query("select f from Friendship f where f.fromUser = ?1 and f.isFriend = true")
    fun findAllByFromUserAndIsFriend(
        user: User
    ): Optional<List<Friendship>>

    @Query("select f from Friendship f where (f.fromUser = ?1 and f.toUser = ?2 and f.isFriend = true) or (f.fromUser = ?2 and f.toUser = ?1 and f.isFriend = true)")
    fun findAllByFromUserAndToUserIsFriend(
        fromUser: User,
        toUser: User
    ): Optional<List<Friendship>>
}
