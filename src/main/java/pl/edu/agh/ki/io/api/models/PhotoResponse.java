package pl.edu.agh.ki.io.api.models;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import pl.edu.agh.ki.io.models.wallElements.Photo;
import pl.edu.agh.ki.io.models.wallElements.reactions.Reaction;

import java.util.List;
import java.util.stream.Collectors;


@Data
@Builder
public class PhotoResponse extends WallItemResponse{
    private Long id;
    private String content;
    private String signedUrl;
    private List<ReactionResponse> reactions;

    public static PhotoResponse fromPhotoAndReactions(Photo photo, List<Reaction> reactions) {
        return PhotoResponse.builder()
                .id(photo.getId())
                .content(photo.getContent())
                .signedUrl(photo.getPhotoPath())
                .reactions(reactions.stream().map(ReactionResponse::fromReaction).collect(Collectors.toList())).build();
    }
}
