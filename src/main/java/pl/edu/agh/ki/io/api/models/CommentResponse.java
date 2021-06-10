package pl.edu.agh.ki.io.api.models;

import lombok.Builder;
import lombok.Data;
import pl.edu.agh.ki.io.models.Comment;

@Data
@Builder
public class CommentResponse {
    private Long id;
    private Long wallItemId;
    private Long userId;
    private String content;

    public static CommentResponse fromComment(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .userId(comment.getUser().getId())
                .wallItemId(comment.getWallItem().getId())
                .build();
    }
}
