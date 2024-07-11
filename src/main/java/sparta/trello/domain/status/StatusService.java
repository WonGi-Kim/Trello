package sparta.trello.domain.status;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sparta.trello.domain.board.Board;
import sparta.trello.domain.board.BoardRepository;
import sparta.trello.domain.status.dto.CreateStatusRequestDto;
import sparta.trello.domain.status.dto.CreateStatusResponseDto;
import sparta.trello.domain.status.dto.StatusUpdateRequestDto;
import sparta.trello.domain.user.User;
import sparta.trello.global.exception.CustomException;
import sparta.trello.global.exception.ErrorCode;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatusService {
    private final StatusRepository statusRepository;
    private final BoardRepository boardRepository;

    public CreateStatusResponseDto createStatus(Long boardId, CreateStatusRequestDto requestDto, User user) {
        Board board = checkBoard(boardId);

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

        checkManager(user.getRole().getValue());

        Status savedStatus = statusRepository.save(status);

        CreateStatusResponseDto responseDto = CreateStatusResponseDto.builder()
                .title(savedStatus.getTitle())
                .createdAt(savedStatus.getCreatedAt())
                .build();

        return responseDto;
    }

    public void deleteStatus(Long boardId, Long statusId, User user) {
        Board board = checkBoard(boardId);
        Status status = statusRepository.findByIdAndBoardId(statusId, board.getId()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_STATUS));

        checkManager(user.getRole().getValue());

        statusRepository.delete(status);
    }

    /**
     * @컬럼_수정_아이디어
     * Drag and Drop이 발생하면, 프론트에서 변경 되어있는 컬럼의 순서를 리스트로 백에 전달
     * 백엔드에서는 요청을 받아 DB 내부의 sequence 업데이트
     */
    public void updateStateSequence(Long boardId, List<StatusUpdateRequestDto> currentStatusSequence, User user) {
        Board board = checkBoard(boardId);
        // DB에서 가져온 Status들
        List<Status> statusList = statusRepository.findByBoardIdOrderBySequence(board.getId());

        checkManager(user.getRole().getValue());

        // 프론트에서 보낸 순서로 Status 엔티티의 sequence 값을 업데이트
        for (StatusUpdateRequestDto requestDto : currentStatusSequence) {
            for (Status status : statusList) {
                if (status.getId().equals(requestDto.getStatusId())) {
                    status.setSequence(requestDto.getSequence());
                    break;
                }
            }
        }


        statusRepository.saveAll(statusList);
    }

    public List<Status> getStatusesByBoardId(Long boardId) {
        return statusRepository.findByBoardIdOrderBySequence(boardId);
    }

    private void checkManager(String userRole) {
        if(!userRole.equals("MANAGER")) {
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }
    }

    private Board checkBoard(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_BOARD));
        return board;
    }
}
