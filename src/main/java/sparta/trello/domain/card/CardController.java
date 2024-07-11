package sparta.trello.domain.card;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import sparta.trello.domain.card.dto.CardRequestDto;
import sparta.trello.domain.card.dto.CardResponseDto;
import sparta.trello.domain.card.dto.NicknameRequestDto;
import sparta.trello.domain.user.User;
import sparta.trello.global.common.CommonResponse;
import sparta.trello.global.security.UserPrincipal;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;

    @PostMapping("/boards/{boardId}/status/{statusId}/cards")
    public ResponseEntity<CommonResponse<CardResponseDto>> createCard
            (@AuthenticationPrincipal UserPrincipal principal, @Valid @RequestBody CardRequestDto requestDto, @PathVariable Long statusId, @PathVariable Long boardId) {
        User user = principal.getUser();
        CardResponseDto responseDto = cardService.create(requestDto, statusId, boardId, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CommonResponse<>("카드 생성 성공", HttpStatus.CREATED.value(), responseDto));
    }

//    @GetMapping("/boards/{boardId}/cards")
//    public ResponseEntity<CommonResponse<List<CardResponseDto>>> findCardList(@PathVariable Long boardId) {
//        List<CardResponseDto> responseDtos = cardService.findCardList(boardId);
//        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>("전체 목록 조회 성공", HttpStatus.OK.value(), responseDtos));
//    }
//
//    @GetMapping("boards/{boardId}/cards")
//    public ResponseEntity<CommonResponse<List<CardResponseDto>>> findCardListByStatus
//            (@PathVariable Long boardId, @RequestParam("status") Long statusId) {
//        List<CardResponseDto> responseDtos = cardService.findCardListByStatus(boardId, statusId);
//        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>("상태 별 목록 조회 성공", HttpStatus.OK.value(), responseDtos));
//    }
//
//    @GetMapping("/boards/{boardId}/cards")
//    public ResponseEntity<CommonResponse<List<CardResponseDto>>> findCardListByUser(@RequestParam("nickname") String nickname, @PathVariable Long boardId) {
//        List<CardResponseDto> responseDtos = cardService.findCardListByUser(nickname, boardId);
//        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>("작성자 별 목록 조회 성공", HttpStatus.OK.value(), responseDtos));
//    }


}
