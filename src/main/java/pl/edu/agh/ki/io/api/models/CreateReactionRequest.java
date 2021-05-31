package pl.edu.agh.ki.io.api.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.agh.ki.io.models.wallElements.reactions.ReactionType;

@Data
@NoArgsConstructor
public class CreateReactionRequest {
    private ReactionType reactionType;
}
