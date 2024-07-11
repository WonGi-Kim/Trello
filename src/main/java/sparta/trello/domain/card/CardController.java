package sparta.trello.domain.card;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sparta.trello.domain.card.dto.CardRequestDto;
import sparta.trello.domain.card.dto.CardResponseDto;
import sparta.trello.global.common.CommonResponse;

@RestController
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;

    @PostMapping("/boards/{boardId}/status/{statusId}/cards")
    public ResponseEntity<CommonResponse<CardResponseDto>> createCard(@RequestBody CardRequestDto requestDto,
                                                                      @PathVariable Long statusId, @PathVariable Long boardId) {

        CardResponseDto responseDto = cardService.create(requestDto, statusId, boardId);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CommonResponse<>("카드 생성 성공", HttpStatus.CREATED.value(), responseDto));
    }
}
