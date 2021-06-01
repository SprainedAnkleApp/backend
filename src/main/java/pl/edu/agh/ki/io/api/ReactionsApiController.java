package pl.edu.agh.ki.io.api;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.ki.io.api.models.CreateReactionRequest;
import pl.edu.agh.ki.io.api.models.ReactionResponse;
import pl.edu.agh.ki.io.db.ReactionsStorage;
import pl.edu.agh.ki.io.models.User;
import pl.edu.agh.ki.io.models.wallElements.reactions.Reaction;
import pl.edu.agh.ki.io.models.wallElements.reactions.ReactionKey;

import java.util.Optional;

@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@Api(tags = "Reactions")
public class ReactionsApiController {
    private final ReactionsStorage reactionsStorage;

    @PostMapping("api/public/wallitems/{wallelementid}")
    public ResponseEntity<ReactionResponse> createReaction(@AuthenticationPrincipal User user, @PathVariable("wallelementid") Long wallElementId, @RequestBody CreateReactionRequest request){
        Reaction reaction = new Reaction(new ReactionKey(user.getId(), wallElementId), request.getReactionType());
        this.reactionsStorage.createReaction(reaction); //nie dziala zmiana reakcji

        return new ResponseEntity<>(ReactionResponse.fromReaction(reaction), HttpStatus.CREATED);
    }

    @DeleteMapping("api/public/wallitems/{wallelementid}")
    public ResponseEntity<ReactionResponse> deleteReaction(@AuthenticationPrincipal User user, @PathVariable("wallelementid") Long wallElementId, @RequestBody CreateReactionRequest request){
        Optional<Reaction> reaction = this.reactionsStorage.findReaction(user.getId(), wallElementId);
        if (reaction.isEmpty()) return ResponseEntity.notFound().build();
        this.reactionsStorage.deleteReaction(reaction.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
