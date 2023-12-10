package mara.server.domain.board

import mara.server.domain.user.UserRepository
import org.springframework.stereotype.Service

@Service
class BoardService(
    private val boardRepository: BoardRepository,
    private val userRepository: UserRepository
) {

    fun createBoard(boardRequest: BoardRequest): Long {
        val userId = boardRequest.userId
        val user = userRepository.findById(userId).orElseThrow { NoSuchElementException("해당 유저가 존재하지 않습니다. ID: $userId") }
        val board = Board(
            title = boardRequest.title,
            writer = user
        )
        return boardRepository.save(board).boardId
    }

    fun getBoard(id: Long): BoardResponse {
        val board = boardRepository.findById(id).orElseThrow { NoSuchElementException("게시판이 존재하지 않습니다. ID: $id") }
        return BoardResponse(board)
    }
}
