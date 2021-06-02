package pl.edu.agh.ki.io.api.models;

import lombok.Builder;
import lombok.Data;
import pl.edu.agh.ki.io.models.User;
import pl.edu.agh.ki.io.models.wallElements.WallItem;
import pl.edu.agh.ki.io.models.wallElements.reactions.Reaction;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class PostResponse extends WallItemResponse{
    private Long id;
    private List<ReactionResponse> reactions;
    private String content;
    private double latitude;
    private double longitude;
    private User user;

    public static PostResponse fromPost(WallItem post) {
        return PostResponse.builder().id(post.getId())
                .content(post.getContent())
                .latitude(post.getLatitude())
                .longitude(post.getLongitude())
                .user(post.getUser())
                .build();
    }

    public static PostResponse fromPostAndReactions(WallItem post, List<Reaction> reactions) {
        return PostResponse.builder().id(post.getId())
                .content(post.getContent())
                .latitude(post.getLatitude())
                .longitude(post.getLongitude())
                .user(post.getUser())
                .reactions(reactions.stream().map(ReactionResponse::fromReaction).collect(Collectors.toList())).build();
    }
}
