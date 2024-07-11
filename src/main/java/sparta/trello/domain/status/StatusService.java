package sparta.trello.domain.status;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sparta.trello.domain.board.Board;
import sparta.trello.domain.board.BoardRepository;
import sparta.trello.domain.status.dto.CreateStatusRequestDto;
import sparta.trello.domain.status.dto.CreateStatusResponseDto;
import sparta.trello.global.exception.CustomException;
import sparta.trello.global.exception.ErrorCode;

@Service
@RequiredArgsConstructor
public class StatusService {
    private final StatusRepository statusRepository;
    private final BoardRepository boardRepository;

    public CreateStatusResponseDto createStatus(Long boardId, CreateStatusRequestDto requestDto) {
        Board board = boardRepository.findById(boardId).orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_BOARD));

        int maxSequence = statusRepository.findMaxSequenceByBoardId(board.getId());
        int createdSequence = maxSequence + 1;

        Status status = Status.builder()
                .title(requestDto.getTitle())
                .sequence(createdSequence)
                .board(board)
                .build();

        if(statusRepository.existsByBoardIdAndTitle(board.getId(), status.getTitle())) {
            throw new CustomException(ErrorCode.ALREADY_EXIST_TITLE);
        }

        // ToDo : 유저 Role파악 후 예외처리 추가

        Status savedStatus = statusRepository.save(status);

        CreateStatusResponseDto responseDto = CreateStatusResponseDto.builder()
                .title(savedStatus.getTitle())
                .createdAt(savedStatus.getCreatedAt())
                .build();

        return responseDto;
    }
}
