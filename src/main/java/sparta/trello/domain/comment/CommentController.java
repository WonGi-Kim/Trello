package sparta.trello.domain.comment;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import sparta.trello.domain.comment.dto.CommentRequestDto;
import sparta.trello.domain.comment.dto.CommentResponseDto;
import sparta.trello.global.security.UserPrincipal;

import java.util.List;

@Tag(name = "Comment API", description = "Comment API 입니다")
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "addComment", description = "댓글 작성 기능입니다.")
    @PostMapping("/cards/{cardId}/comments")
    public ResponseEntity<CommentResponseDto> addCommentToCard(
            @PathVariable Long cardId,
            @Valid @RequestBody CommentRequestDto requestDto,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        CommentResponseDto responseDto = new CommentResponseDto(
                commentService.addComment(cardId, requestDto, userPrincipal.getUser()));

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED); // 생성된 댓글 포함한 응답 반환
    }

    @Operation(summary = "getComment", description = "댓글 조회 기능입니다.")
    @GetMapping("/cards/{cardId}/comments")
    public ResponseEntity<List<CommentResponseDto>> getCommentsByCardId(
            @PathVariable Long cardId) {

        List<CommentResponseDto> comments = commentService.getCommentsByCardId(cardId);

        return new ResponseEntity<>(comments, HttpStatus.OK);
    }
}
