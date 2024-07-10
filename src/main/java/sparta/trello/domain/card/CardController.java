package sparta.trello.domain.card;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sparta.trello.global.common.CommonResponse;

@RestController
@RequestMapping("/cards")
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;

    @PostMapping("/{boardId}/{columnId}")
    public CommonResponse<CardResponseDto> createCard(HttpServletResponse response, @RequestBody CardRequestDto requestDto,
                                                      @PathVariable Long columnId, @PathVariable Long boardId) {

        CardResponseDto responseDto = cardService.create(requestDto, columnId, boardId);
        return new CommonResponse<>("카드 생성이 되었습니다.", response.getStatus(), responseDto);
    }
}
