package sparta.trello.domain.card.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CardRequestDto {
    @NotBlank(message = "제목은 필수 입력값입니다.")
    @Size(max=255, message = "제목은 255자리 이하로 입력해주세요")
    String title;
    String content;
    LocalDate deadline;
}
