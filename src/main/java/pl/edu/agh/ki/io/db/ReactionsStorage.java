package pl.edu.agh.ki.io.db;

import org.springframework.stereotype.Service;
import pl.edu.agh.ki.io.models.wallElements.reactions.Reaction;
import pl.edu.agh.ki.io.models.wallElements.reactions.ReactionKey;
import pl.edu.agh.ki.io.models.wallElements.reactions.ReactionType;

import java.util.Optional;

@Service
public class ReactionsStorage {
    private final ReactionsRepository reactionsRepository;


    public ReactionsStorage(ReactionsRepository reactionsRepository) {
        this.reactionsRepository = reactionsRepository;
    }

    public void createReaction(Reaction reaction) {
        this.reactionsRepository.save(reaction);
    }

    public void deleteReaction(Reaction reaction) {
        if(this.reactionsRepository.findById(reaction.getId()).isPresent())
        this.reactionsRepository.delete(reaction);
    }

    public Optional<Reaction> findReaction(Long userId, Long wallElementId) {
        return this.reactionsRepository.findById(new ReactionKey(userId, wallElementId));
    }
}
