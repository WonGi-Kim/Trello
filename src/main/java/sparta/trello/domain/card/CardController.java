package sparta.trello.domain.card;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sparta.trello.domain.card.dto.CardRequestDto;
import sparta.trello.domain.card.dto.CardResponseDto;
import sparta.trello.domain.card.dto.NicknameRequestDto;
import sparta.trello.global.common.CommonResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;

    @PostMapping("/boards/{boardId}/status/{statusId}/cards")
    public ResponseEntity<CommonResponse<CardResponseDto>> createCard(@Valid @RequestBody CardRequestDto requestDto,
                                                                      @PathVariable Long statusId, @PathVariable Long boardId) {

        CardResponseDto responseDto = cardService.create(requestDto, statusId, boardId);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CommonResponse<>("카드 생성 성공", HttpStatus.CREATED.value(), responseDto));
    }

    @GetMapping("/cards")
    public ResponseEntity<CommonResponse<List<CardResponseDto>>> findCardList(){
        List<CardResponseDto> responseDtos = cardService.findCardList();
        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>("전체 목록 조회 성공", HttpStatus.OK.value(), responseDtos));
    }

    @GetMapping("/status/{statusId}/cards")
    public ResponseEntity<CommonResponse<List<CardResponseDto>>> findCartListByStatus(@PathVariable Long statusId){
        List<CardResponseDto> responseDtos = cardService.findCardListByStatus(statusId);
        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>("상태 별 목록 조회 성공", HttpStatus.OK.value(), responseDtos));
    }

    @GetMapping("/user/cards")
    public ResponseEntity<CommonResponse<List<CardResponseDto>>> findCartListByUser(@RequestBody NicknameRequestDto requestDto){
        List<CardResponseDto> responseDtos = cardService.findCardListByUser(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>("작성자 별 목록 조회 성공", HttpStatus.OK.value(), responseDtos));
    }


}
