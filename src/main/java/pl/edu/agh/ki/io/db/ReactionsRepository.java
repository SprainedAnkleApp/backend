package pl.edu.agh.ki.io.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.agh.ki.io.models.wallElements.reactions.Reaction;
import pl.edu.agh.ki.io.models.wallElements.reactions.ReactionKey;

import java.util.List;

@Repository
public interface ReactionsRepository extends JpaRepository<Reaction, ReactionKey> {

    List<Reaction> findByIdWallElementID(Long wallElementId);
}
