package sparta.trello.domain.status.dto;

import lombok.Data;

@Data
public class StatusUpdateRequestDto {
    private Long statusId;
    private int sequence;
}
