package mara.server.domain.board

import mara.server.common.CommonResponse
import mara.server.common.success
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/boards")
class BoardController(private val boardService: BoardService) {

    @PostMapping
    fun createBoard(@RequestBody boardRequest: BoardRequest,): CommonResponse<Long> {
        return success(boardService.createBoard(boardRequest))
    }

    @GetMapping("/{id}")
    fun getBoard(@PathVariable(name = "id") id: Long): CommonResponse<BoardResponse> {
        return success(boardService.getBoard(id))
    }
}
