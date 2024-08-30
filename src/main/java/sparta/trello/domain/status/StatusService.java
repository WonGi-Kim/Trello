package sparta.trello.domain.status;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sparta.trello.domain.board.Board;
import sparta.trello.domain.board.BoardRepository;
import sparta.trello.domain.status.dto.CreateStatusRequestDto;
import sparta.trello.domain.status.dto.CreateStatusResponseDto;
import sparta.trello.domain.status.dto.StatusResponseDto;
import sparta.trello.domain.status.dto.StatusUpdateRequestDto;
import sparta.trello.domain.user.User;
import sparta.trello.global.exception.CustomException;
import sparta.trello.global.exception.ErrorCode;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatusService {
    private final StatusRepository statusRepository;
    private final BoardRepository boardRepository;

    // 성능 체크용 repository
    private final StatusSecondRepository statusSecondRepository;

    // 성능 체크용 클래스
    public void comparePerformance(Long statusId, Long boardId) {
        // QueryDSL 방식 성능 측정
        long start = System.nanoTime();
        statusRepository.findByIdAndBoardId(statusId, boardId);
        long end = System.nanoTime();
        System.out.println("QueryDSL <<<findByIdAndBoardId>>> 실행 시간: " + (end - start) + " ns");

        start = System.nanoTime();
        statusSecondRepository.findByIdAndBoardId(statusId, boardId);
        end = System.nanoTime();
        System.out.println("JPA <<<findByIdAndBoardId>>> 실행 시간: " + (end - start) + " ns");

        start = System.nanoTime();
        statusRepository.findByBoardIdOrderBySequence(boardId);
        end = System.nanoTime();
        System.out.println("QueryDSL <<<findByBoardIdOrderBySequence>>> 실행 시간: " + (end - start) + " ns");

        start = System.nanoTime();
        statusSecondRepository.findByBoardIdOrderBySequence(boardId);
        end = System.nanoTime();
        System.out.println("QueryDSL <<<findByBoardIdOrderBySequence>>> 실행 시간: " + (end - start) + " ns");
    }

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

    public void updateStateSequence(Long boardId, List<StatusUpdateRequestDto> currentStatusSequence, User user) {
        Board board = checkBoard(boardId);
        // DB에서 가져온 Status들
        List<Status> statusList = statusRepository.findByBoardIdOrderBySequence(board.getId());

        checkManager(user.getRole().getValue());

        //  기존 코드의 O(MN) 해소를 위해 StatusList를 HashMap으로 변경
        Map<Long, Status> statusMap = statusList.stream()
                .collect(Collectors.toMap(Status::getId, status -> status));

        // 프론트에서 보낸 순서로 Status 엔티티의 sequence 값을 업데이트
        for (StatusUpdateRequestDto requestDto : currentStatusSequence) {
            Status status = statusMap.get(requestDto.getStatusId());
            if(status != null) {
                status.setSequence(requestDto.getSequence());
            }
        }
        statusRepository.saveAll(statusList);
    }

    public List<StatusResponseDto> getStatusesByBoardId(Long boardId) {
        List<Status> statuses = statusRepository.findByBoardIdOrderBySequence(boardId);

        return statuses.stream()
                .map(status -> new StatusResponseDto(
                        status.getId(),
                        status.getTitle(),
                        status.getSequence(),
                        status.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }

    private void checkManager(String userRole) {
        if(!userRole.equals("MANAGER")) {
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }
    }

    private Board checkBoard(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_BOARD));
    }

    public CreateStatusResponseDto dummyCreateStatus(Long boardId, int count, User user) {
        Board board = checkBoard(boardId);
        Faker faker = new Faker();

        checkManager(user.getRole().getValue());

        try(FileWriter writer = new FileWriter("data.csv")){
            writer.append("title,sequence,board_id\n");
            int maxSequence = statusRepository.findMaxSequenceByBoardId(board.getId());
            for(int i=1; i<=count; i++){
                String title = faker.leagueOfLegends().champion();

                int createdSequence = maxSequence + i;
                Long board_id = boardId;

                writer.append(title).append(",")
                        .append(Integer.toString(createdSequence)).append(",")
                        .append(board_id.toString()).append("\n");

            }
            System.out.println("CSV 파일 생성 완료!");
        } catch (IOException e) {
            System.out.println("CSV 파일 생성 중 오류 발생:");
            e.printStackTrace();
        }
        CreateStatusResponseDto responseDto = CreateStatusResponseDto.builder()
                .title("더미 클리어")
                .build();

        return responseDto;
    }
}
