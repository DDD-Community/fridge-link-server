package mara.server.domain.friend

import mara.server.domain.user.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface FriendshipRepository : JpaRepository<Friendship, Long> {

    @Query("select f from Friendship f where (f.fromUser = ?1 and f.toUser = ?2) or (f.toUser = ?3 and f.fromUser = ?4)")
    fun findByFromUserAndToUserOrToUserAndFromUser(
        fromUser1: User,
        toUser1: User,
        toUser2: User,
        fromUser2: User
    ): Friendship

}