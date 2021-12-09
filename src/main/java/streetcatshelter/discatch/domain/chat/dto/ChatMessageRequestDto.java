package streetcatshelter.discatch.domain.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import streetcatshelter.discatch.domain.chat.domain.ChatMessage;

@Getter
@AllArgsConstructor
public class ChatMessageRequestDto {
    private ChatMessage.MessageType type;
    private String roomId;
    private Long senderId;
    private String message;
}