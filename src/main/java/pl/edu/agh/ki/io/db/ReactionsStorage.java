package pl.edu.agh.ki.io.db;

import org.springframework.stereotype.Service;
import pl.edu.agh.ki.io.models.wallElements.reactions.Reaction;
import pl.edu.agh.ki.io.models.wallElements.reactions.ReactionKey;

import java.util.List;
import java.util.Optional;

@Service
public class ReactionsStorage {
    private final ReactionsRepository reactionsRepository;


    public ReactionsStorage(ReactionsRepository reactionsRepository) {
        this.reactionsRepository = reactionsRepository;
    }

    public void createReaction(Reaction reaction) {
        Optional<Reaction> optionalReaction = this.reactionsRepository.findById(reaction.getId());
        if (optionalReaction.isPresent()) {
            optionalReaction.get().setType(reaction.getType());
            this.reactionsRepository.save(optionalReaction.get());
        }
        else this.reactionsRepository.save(reaction);
    }

    public void deleteReaction(Reaction reaction) {
        if(this.reactionsRepository.findById(reaction.getId()).isPresent())
        this.reactionsRepository.delete(reaction);
    }

    public Optional<Reaction> findReaction(Long userId, Long wallElementId) {
        return this.reactionsRepository.findById(new ReactionKey(userId, wallElementId));
    }

    public List<Reaction> findByIdWallElementID(Long id) {
        return this.reactionsRepository.findByIdWallElementID(id);
    }
}
