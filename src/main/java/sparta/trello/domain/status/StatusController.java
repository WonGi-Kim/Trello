package sparta.trello.domain.status;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import sparta.trello.domain.status.dto.CreateStatusRequestDto;
import sparta.trello.domain.status.dto.CreateStatusResponseDto;
import sparta.trello.domain.status.dto.StatusUpdateRequestDto;
import sparta.trello.global.common.CommonResponse;
import sparta.trello.global.security.UserPrincipal;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class StatusController {
    private final StatusService statusService;

    @PostMapping("/boards/{boardId}/status")
    public ResponseEntity<CommonResponse> createStatus(@PathVariable("boardId") Long boardId,
                                                       @Valid @RequestBody CreateStatusRequestDto requestDto,
                                                       @AuthenticationPrincipal UserPrincipal principal) {
        CreateStatusResponseDto responseDto = statusService.createStatus(boardId, requestDto, principal.getUser());
        CommonResponse response = new CommonResponse<>("컬럼 생성 완료", 201, responseDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/boards/{boardId}/status/{statusId}")
    public ResponseEntity<CommonResponse> deleteStatus(@PathVariable("boardId") Long boardId,
                                                       @PathVariable("statusId") Long statusId,
                                                       @AuthenticationPrincipal UserPrincipal principal) {
        statusService.deleteStatus(boardId, statusId, principal.getUser());
        CommonResponse response = new CommonResponse<>("컬럼 삭제 완료", 200, "");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/boards/{boardId}/status/orders")
    public ResponseEntity<CommonResponse> updateStatusSequences(@PathVariable("boardId") Long boardId,
                                                                @RequestBody List<StatusUpdateRequestDto> currentStatusSequence,
                                                                @AuthenticationPrincipal UserPrincipal principal) {
        statusService.updateStateSequence(boardId, currentStatusSequence, principal.getUser());
        CommonResponse response = new CommonResponse<>("컬럼 순서이동 완료", 200, "");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
