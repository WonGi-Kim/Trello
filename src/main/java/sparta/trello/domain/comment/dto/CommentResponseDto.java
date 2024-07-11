package sparta.trello.domain.comment.dto;

import lombok.*;
import sparta.trello.domain.comment.Comment;

@Data
public class CommentResponseDto {
    private Long id;
    private String content;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
    }
}
