package sparta.trello.domain.status;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sparta.trello.domain.status.dto.CreateStatusRequestDto;
import sparta.trello.domain.status.dto.CreateStatusResponseDto;
import sparta.trello.global.common.CommonResponse;

@Valid
@RestController
public class StatusController {
    @Autowired
    private StatusService statusService;

    @PostMapping("/boards/{boardId}/status")
    public ResponseEntity<CommonResponse> createStatus(@PathVariable("boardId") Long boardId, @RequestBody CreateStatusRequestDto requestDto) {
        CreateStatusResponseDto responseDto = statusService.createStatus(boardId, requestDto);
        CommonResponse response = new CommonResponse<>("컬럼 생성 완료", 201, responseDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
