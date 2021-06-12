package pl.edu.agh.ki.io.db;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.edu.agh.ki.io.models.ChatMessage;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    @Query(value = "select cm from ChatMessage cm " +
            "where (cm.sender.id = :senderId and cm.receiver.id = :receiverId) or " +
            "      (cm.sender.id = :receiverId and cm.receiver.id = :senderId) " +
            "order by cm.createDate DESC")
    Page<ChatMessage> findChatBySenderIdAndReceiverId(@Param("senderId") Long senderId,
                                                      @Param("receiverId") Long receiverId,
                                                      Pageable pageable);

    @Query(value = "select cm from ChatMessage cm " +
            "where cm.receiver.id = :receiverId and cm.seen = false " +
            "order by cm.createDate DESC")
    Page<ChatMessage> findUnseenByReceiverId(@Param("receiverId") Long receiverId,
                                             Pageable pageable);
}
