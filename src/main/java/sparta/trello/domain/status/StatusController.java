package sparta.trello.domain.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sparta.trello.domain.status.dto.CreateStatusRequestDto;
import sparta.trello.global.common.CommonResponse;

@RestController
public class StatusController {
    @Autowired
    private StatusService statusService;

    @PostMapping("/boards/{boardId}/status")
    public CommonResponse createStatus(@PathVariable("boardId") Long boardId, @RequestBody CreateStatusRequestDto requestDto) {
        CommonResponse response = statusService.createStatus(boardId, requestDto);

        return response;
    }

}
