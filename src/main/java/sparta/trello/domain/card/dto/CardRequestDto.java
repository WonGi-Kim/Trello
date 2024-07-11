package sparta.trello.domain.card.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CardRequestDto {
    @NotBlank(message = "제목은 필수 입력값입니다.")
    @Max(value = 255)
    String title;
    String content;
    LocalDate deadline;
}
