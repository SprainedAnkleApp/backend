package pl.edu.agh.ki.io.db;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.agh.ki.io.models.ChatMessage;

@Service
@RequiredArgsConstructor
public class ChatMessageStorage {
    private final ChatMessageRepository chatMessageRepository;

    public ChatMessage save(ChatMessage chatMessage) {
        return this.chatMessageRepository.save(chatMessage);
    }
}
