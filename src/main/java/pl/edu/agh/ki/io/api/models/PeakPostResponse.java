package pl.edu.agh.ki.io.api.models;

import lombok.Builder;
import lombok.Data;
import pl.edu.agh.ki.io.models.Peak;
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

    public static PeakPostResponse fromPeakPostAndReactions(PeakPost peakPost, List<Reaction> reactions) {
        return PeakPostResponse.builder()
                .id(peakPost.getId())
                .content(peakPost.getContent())
                .peak(peakPost.getPeak())
                .reactions(reactions.stream().map(ReactionResponse::fromReaction).collect(Collectors.toList())).build();
    }
}
