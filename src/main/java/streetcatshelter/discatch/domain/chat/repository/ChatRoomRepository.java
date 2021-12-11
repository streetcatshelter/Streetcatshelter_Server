package streetcatshelter.discatch.domain.chat.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import streetcatshelter.discatch.domain.chat.domain.ChatRoom;
import streetcatshelter.discatch.domain.user.domain.User;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    List<ChatRoom> findAllByUser(User user);
    ChatRoom findByRoomId(String roomId);
    @NotNull Optional<ChatRoom> findById(@NotNull Long roomId);
    ChatRoom findByUserSeqSum(String userSeqSum);
    int countAllByRoomId(String roomId);
}