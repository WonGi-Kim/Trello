package sparta.trello.domain.status;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sparta.trello.domain.status.dto.CreateStatusRequestDto;
import sparta.trello.domain.status.dto.CreateStatusResponseDto;
import sparta.trello.global.common.CommonResponse;


@RestController
@RequiredArgsConstructor
public class StatusController {
    private final StatusService statusService;

    @PostMapping("/boards/{boardId}/status")
    public ResponseEntity<CommonResponse> createStatus(@PathVariable("boardId") Long boardId, @Valid @RequestBody CreateStatusRequestDto requestDto) {
        // ToDo : User정보 받아올 수 있도록 추가
        CreateStatusResponseDto responseDto = statusService.createStatus(boardId, requestDto);
        CommonResponse response = new CommonResponse<>("컬럼 생성 완료", 201, responseDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/boards/{boardId}/status/{statusId}")
    public ResponseEntity<CommonResponse> deleteStatus(@PathVariable("boardId") Long boardId, @PathVariable("statusId") Long statusId) {
        statusService.deleteStatus(boardId, statusId);
        CommonResponse response = new CommonResponse<>("컬럼 삭제 완료", 200, "");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
