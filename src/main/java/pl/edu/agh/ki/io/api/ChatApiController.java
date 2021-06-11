package pl.edu.agh.ki.io.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.ki.io.api.models.SendMessageRequest;
import pl.edu.agh.ki.io.api.models.SetSeenRequest;
import pl.edu.agh.ki.io.api.models.UserResponse;
import pl.edu.agh.ki.io.db.ChatMessageStorage;
import pl.edu.agh.ki.io.db.UserStorage;
import pl.edu.agh.ki.io.models.ChatMessage;
import pl.edu.agh.ki.io.models.PageParameters;
import pl.edu.agh.ki.io.models.User;

import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
public class ChatApiController {
    final SimpMessagingTemplate template;
    final UserStorage userStorage;
    final ChatMessageStorage chatMessageStorage;

    @MessageMapping("/")
    public void sendMessage(@Payload SendMessageRequest messageRequest){
        Optional<User> sender = userStorage.findUserById(messageRequest.getSenderId());
        Optional<User> receiver = userStorage.findUserById(messageRequest.getSendTo());
        if (receiver.isPresent() && sender.isPresent()) {
            ChatMessage message = new ChatMessage(sender.get(), receiver.get(), messageRequest.getMessage());
            message = chatMessageStorage.save(message);
            template.convertAndSend("/messages/" + messageRequest.getSendTo(), message);
        }
    }

    @GetMapping("/api/chat/{userId}")
    public ResponseEntity<Page<ChatMessage>> getChat(@AuthenticationPrincipal User sender,
                                                     @PathVariable("userId") Long receiverId, PageParameters pageParameters) {
        Page<ChatMessage> messages = chatMessageStorage.findChatMessages(sender.getId(), receiverId, pageParameters);
        Pageable pageable = PageRequest.of(pageParameters.getPageNumber(),
                pageParameters.getPageSize());
        messages.forEach(message -> {
            message.setSeen(true);
            chatMessageStorage.save(message);
        });
        return new ResponseEntity<>(new PageImpl<>(messages.stream()
                .collect(Collectors.toList()), pageable, messages.getTotalElements()), HttpStatus.OK);
    }

    @PostMapping("/api/chat/seen")
    public ResponseEntity<?> setSeen(@AuthenticationPrincipal User user, SetSeenRequest request) {
        Optional<ChatMessage> messageOptional = chatMessageStorage.findMassageById(request.getMessageId());
        return messageOptional
                .map(message -> {
                    if(message.getReceiver().getId().equals(user.getId()))
                        return ResponseEntity.status(403).build();
                    message.setSeen(true);
                    message = chatMessageStorage.save(message);
                    return new ResponseEntity<>(message, HttpStatus.OK);
                }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/api/chat/unseen")
    public ResponseEntity<Page<ChatMessage>> getUnseen(@AuthenticationPrincipal User user, PageParameters pageParameters) {
        Page<ChatMessage> messages = chatMessageStorage.findUnseen(user.getId(), pageParameters);
        Pageable pageable = PageRequest.of(pageParameters.getPageNumber(),
                pageParameters.getPageSize());
        return new ResponseEntity<>(new PageImpl<>(messages.stream()
                .collect(Collectors.toList()), pageable, messages.getTotalElements()), HttpStatus.OK);
    }
}
