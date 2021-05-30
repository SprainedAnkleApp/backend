package pl.edu.agh.ki.io.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.agh.ki.io.models.wallElements.reactions.Reaction;
import pl.edu.agh.ki.io.models.wallElements.reactions.ReactionKey;

@Repository
public interface ReactionsRepository extends JpaRepository<Reaction, ReactionKey> {
}
