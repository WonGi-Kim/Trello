package sparta.trello.domain.board;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sparta.trello.domain.board.dto.BoardRequestDto;
import sparta.trello.domain.board.dto.BoardResponseDto;
import sparta.trello.global.common.CommonResponse;
import sparta.trello.global.security.UserPrincipal;

import java.util.List;

@Tag(name = "Board API", description = "Board API 입니다")
@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @Operation(summary = "addBoard", description = "보드 생성 기능입니다.")
    @PostMapping("/boards")
    public ResponseEntity<CommonResponse<BoardResponseDto>> addBoard(@Valid @RequestBody BoardRequestDto boardRequestDto,
                                                                   @AuthenticationPrincipal UserPrincipal userPrincipal) {
        CommonResponse<BoardResponseDto> commonResponseDto = new CommonResponse<>(
                "보드 생성 성공",
                HttpStatus.CREATED.value(),
                boardService.addBoard(boardRequestDto, userPrincipal.getUser())
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponseDto);
    }

    @Operation(summary = "getBoards", description = "보드 조회 기능입니다.")
    @GetMapping("/boards")
    public ResponseEntity<CommonResponse<List<BoardResponseDto>>> getBoards(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        CommonResponse<List<BoardResponseDto>> commonResponseDto = new CommonResponse<>(
                "보드 조회 성공",
                HttpStatus.OK.value(),
                boardService.getBoards(userPrincipal.getUser())
        );
        return ResponseEntity.ok().body(commonResponseDto);
    }
}
