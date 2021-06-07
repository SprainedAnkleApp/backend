package pl.edu.agh.ki.io.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.ki.io.api.models.SendMessageRequest;
import pl.edu.agh.ki.io.db.ChatMessageStorage;
import pl.edu.agh.ki.io.db.UserStorage;
import pl.edu.agh.ki.io.models.ChatMessage;
import pl.edu.agh.ki.io.models.User;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ChatApiController {
    final SimpMessagingTemplate template;
    final UserStorage userStorage;
    final ChatMessageStorage chatMessageStorage;

    @MessageMapping("/")
    public void sendMessage(@AuthenticationPrincipal User sender, @Payload SendMessageRequest messageRequest){
        Optional<User> receiver = userStorage.findUserById(messageRequest.getSendTo());

        if (receiver.isPresent()) {
            ChatMessage message = new ChatMessage(sender, receiver.get(), messageRequest.getMessage());
            message = chatMessageStorage.save(message);
            template.convertAndSend("/messages/" + messageRequest.getSendTo(), message);
        }
    }

    @GetMapping("/api/chat/{userId}")
    public ResponseEntity<Page<ChatMessage>> getChat(@AuthenticationPrincipal User sender, @PathVariable("userId") Long receiverId) {
        //chatMessageStorage.findChatMessages(sender.getId(), receiverId);
        return ResponseEntity.ok().build(); //TODO
    }
}
