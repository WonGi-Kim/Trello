package sparta.trello.domain.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sparta.trello.domain.board.Board;
import sparta.trello.domain.board.BoardRepository;
import sparta.trello.domain.status.dto.CreateStatusRequestDto;
import sparta.trello.domain.status.dto.CreateStatusResponseDto;
import sparta.trello.global.common.CommonResponse;
import sparta.trello.global.exception.CustomException;
import sparta.trello.global.exception.ErrorCode;

@Service
public class StatusService {
    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private BoardRepository boardRepository;

    public CommonResponse createStatus(Long boardId, CreateStatusRequestDto requestDto) {
        Board board = boardRepository.findById(boardId).orElseThrow(()-> new IllegalArgumentException("Board not found"));

        int maxSequence = statusRepository.findMaxSequenceByBoardId(board.getId());
        int createdSequence = maxSequence + 1;

        Status status = Status.builder()
                .title(requestDto.getTitle())
                .sequence(createdSequence)
                .board(board)
                .build();

        if(statusRepository.existsByBoardIdAndStatusTitle(board.getId(), status.getTitle())) {
            throw new CustomException(ErrorCode.ALREADY_EXIST_TITLE);
        }

        if(status.getTitle() == null) {
            throw new CustomException(ErrorCode.NOT_FOUND_STATUS_TITLE);
        }

        Status savedStatus = statusRepository.save(status);

        CreateStatusResponseDto responseDto = CreateStatusResponseDto.builder()
                .title(savedStatus.getTitle())
                .createdAt(savedStatus.getCreatedAt())
                .build();

        return new CommonResponse<>("보드 생성 완료", 201, responseDto);
    }
}
