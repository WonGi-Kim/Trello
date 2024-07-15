package sparta.trello.domain.comment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentRequestDto {
    @NotBlank(message = "댓글 내용을 작성해주세요.")
    private String content;

    @Builder
    public CommentRequestDto(String content) {
        this.content = content;
    }
}
