package pl.edu.agh.ki.io.api.models;

import lombok.Builder;
import lombok.Data;
import pl.edu.agh.ki.io.models.wallElements.reactions.Reaction;
import pl.edu.agh.ki.io.models.wallElements.reactions.ReactionType;

@Data
@Builder
public class ReactionResponse {
    private ReactionType type;
    private Long userId;

    public static ReactionResponse fromReaction(Reaction reaction) {
        return ReactionResponse.builder()
                .type(reaction.getType())
                .userId(reaction.getId().getUserId()).build();
    }
}
