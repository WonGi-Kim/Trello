package sparta.trello.domain.card;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import sparta.trello.domain.card.dto.*;
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

    @GetMapping("/boards/{boardId}/cards")
    public ResponseEntity<CommonResponse<List<CardResponseDto>>> findCardList(@PathVariable Long boardId) {
        List<CardResponseDto> responseDtos = cardService.findCardList(boardId);
        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>("전체 목록 조회 성공", HttpStatus.OK.value(), responseDtos));
    }

//    @GetMapping("/boards/{boardId}/cards")
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

    @DeleteMapping("/boards/{boardId}/cards/{cardId}")
    public ResponseEntity<CommonResponse> deleteCard(@AuthenticationPrincipal UserPrincipal principal, @PathVariable Long boardId, @PathVariable Long cardId){
        User user = principal.getUser();
        cardService.deleteCard(boardId, cardId, user);
        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>("삭제 성공", HttpStatus.OK.value(), ""));
    }

    @PatchMapping("/boards/{boardId}/cards/{cardId}")
    public ResponseEntity<CommonResponse<CardUpdateResponseDto>> updateCard
            (@AuthenticationPrincipal UserPrincipal principal, @PathVariable Long boardId, @PathVariable Long cardId, @RequestBody CardUpdateRequestDto requestDto){
        User user = principal.getUser();
        CardUpdateResponseDto responseDto = cardService.updateCard(boardId, cardId, requestDto, user);
        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>("수정 성공", HttpStatus.OK.value(), responseDto));
    }

    /**
     * 가정
     * 카드 이동 : 프론트에서 카드 칼럼이 변경된 상태 ID, 현재 카드 ID를 받아온 값 -> PathVariable로 넣어줘서 사용하게 만듬.
     */

    @PutMapping("/cards/{cardId}/orders/{newStatusId}")
    public ResponseEntity<CommonResponse> changeStatus
            (@AuthenticationPrincipal UserPrincipal principal, @PathVariable Long cardId, @PathVariable Long newStatusId){
        User user = principal.getUser();
        cardService.changeStatus(cardId, newStatusId, user);
        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>("변경 성공", HttpStatus.OK.value(), ""));
    }


}
