package streetcatshelter.discatch.domain.chat.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ChatRoomResponseDto {
    private String opponent;
    private String opponentImage;
    private String lastActivity;
    private String roomId;
    private String lastMessage;
    private String userRandomId;
    private int cntChat;
}
