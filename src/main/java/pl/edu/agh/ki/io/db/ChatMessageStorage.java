package pl.edu.agh.ki.io.db;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.edu.agh.ki.io.models.ChatMessage;
import pl.edu.agh.ki.io.models.PageParameters;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatMessageStorage {
    private final ChatMessageRepository chatMessageRepository;

    public ChatMessage save(ChatMessage chatMessage) {
        return this.chatMessageRepository.save(chatMessage);
    }

    public Page<ChatMessage> findChatMessages(Long senderId, Long receiverId, PageParameters pageParameters) {
        Pageable pageable = PageRequest.of(pageParameters.getPageNumber(),
                pageParameters.getPageSize());
        return this.chatMessageRepository.findChatBySenderIdAndReceiverId(senderId, receiverId, pageable);
    }

    public Optional<ChatMessage> findMassageById(Long messageId) {
        return this.chatMessageRepository.findById(messageId);
    }

    public Page<ChatMessage> findUnseen(Long userId, PageParameters pageParameters) {
        Pageable pageable = PageRequest.of(pageParameters.getPageNumber(),
                pageParameters.getPageSize());

        return this.chatMessageRepository.findUnseenByReceiverId(userId, pageable);
    }
}
