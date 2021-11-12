package streetcatshelter.discatch.domain.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import streetcatshelter.discatch.domain.chat.domain.ChatMessage;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findAllByRoomIdOrderByTimenowDesc(String roomId);

    ChatMessage findFirstByRoomIdOrderByTimenowDesc(String roomId);
}