package sparta.trello.domain.card;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sparta.trello.global.common.CommonResponse;

@RestController
@RequestMapping("/cards")
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;

    @PostMapping("/boards/{boardId}/status/{statusId}/cards")
    public ResponseEntity<CommonResponse<CardResponseDto>> createCard(@RequestBody CardRequestDto requestDto,
                                     @PathVariable Long columnId, @PathVariable Long boardId) {

        CardResponseDto responseDto = cardService.create(requestDto, columnId, boardId);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CommonResponse<>("카드 생성 성공", HttpStatus.CREATED.value(), responseDto));
    }
}
