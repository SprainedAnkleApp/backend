package pl.edu.agh.ki.io.db;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.agh.ki.io.models.ChatMessage;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

}
