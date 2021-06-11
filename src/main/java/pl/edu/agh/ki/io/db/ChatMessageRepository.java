package pl.edu.agh.ki.io.db;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.edu.agh.ki.io.models.ChatMessage;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    @Query(value = "select cm from ChatMessage cm " +
            "where (cm.sender = :senderId and cm.receiver = :receiverId) or " +
            "      (cm.sender = :receiverId and cm.receiver = :senderId) " +
            "order by cm.createDate DESC")
    Page<ChatMessage> findChatBySenderIdAndReceiverId(@Param("senderId") Long senderId,
                                                      @Param("receiverId") Long receiverId,
                                                      Pageable pageable);

    @Query(value = "select cm from ChatMessage cm " +
            "where cm.receiver = :receiverId and cm.seen = false " +
            "order by cm.createDate DESC")
    Page<ChatMessage> findUnseenByReceiverId(@Param("receiverId") Long receiverId,
                                             Pageable pageable);
}
