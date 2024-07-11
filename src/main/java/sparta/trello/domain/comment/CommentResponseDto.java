package sparta.trello.domain.comment;

import lombok.*;

@Data
public class CommentResponseDto {
    private Long id;
    private String content;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
    }
}
