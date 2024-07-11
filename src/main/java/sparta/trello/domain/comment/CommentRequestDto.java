package sparta.trello.domain.comment;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
public class CommentRequestDto {
    @NotBlank(message = "댓글 내용을 작성해주세요.")
    private String content;

    @Builder
    public CommentRequestDto(String content) {
        this.content = content;
    }
}
