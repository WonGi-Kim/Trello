package sparta.trello.domain.status.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateStatusRequestDto {
    @NotBlank(message = "필수 입력 정보는 공백일 수 없습니다.")
    @Size(max = 50)
    private String title;
}
