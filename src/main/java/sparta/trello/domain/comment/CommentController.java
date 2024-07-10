package sparta.trello.domain.comment;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cards")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{cardId}/comments")
    public ResponseEntity<CommentResponseDto> addCommentToCard(
            @PathVariable Long cardId,
            @RequestParam Long userId,
            @RequestBody CommentRequestDto requestDto) {

        CommentResponseDto responseDto = new CommentResponseDto(
                commentService.addComment(cardId, userId, requestDto));

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED); // 생성된 댓글 포함한 응답 반환
    }

}
