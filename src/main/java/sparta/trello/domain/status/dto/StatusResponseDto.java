package sparta.trello.domain.status.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StatusResponseDto {
    private Long statusId;
    private String title;
    private int sequence;
    private LocalDateTime createAt;

    public StatusResponseDto(Long statusId, String title, int sequence, LocalDateTime createAt) {
        this.statusId = statusId;
        this.title = title;
        this.sequence = sequence;
        this.createAt = createAt;
    }
}
