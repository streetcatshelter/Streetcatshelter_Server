package streetcatshelter.discatch.domain.chat.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class ChatRoomResponseDto {
    private String opponent;
    private String opponentImage;
    private LocalDateTime lastActivity;
    private String roomId;
    private String lastMessage;
}
