package pl.edu.agh.ki.io.api.models;

import lombok.Builder;
import lombok.Data;
import pl.edu.agh.ki.io.models.Peak;
import pl.edu.agh.ki.io.models.User;
import pl.edu.agh.ki.io.models.wallElements.PeakPost;
import pl.edu.agh.ki.io.models.wallElements.reactions.Reaction;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class PeakPostResponse extends WallItemResponse {
    private Long id;
    private String content;
    private Peak peak;
    private List<ReactionResponse> reactions;
    private double latitude;
    private double longitude;
    private User user;

    public static PeakPostResponse fromPeakPostAndReactions(PeakPost peakPost, List<Reaction> reactions) {
        return PeakPostResponse.builder()
                .id(peakPost.getId())
                .content(peakPost.getContent())
                .peak(peakPost.getPeak())
                .latitude(peakPost.getLatitude())
                .longitude(peakPost.getLongitude())
                .user(peakPost.getUser())
                .reactions(reactions.stream().map(ReactionResponse::fromReaction).collect(Collectors.toList())).build();
    }
}
