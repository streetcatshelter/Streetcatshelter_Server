package streetcatshelter.discatch.domain.chat.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ChatMessageResponseDto {
    private String message;
    private String sender;
    private String time;
    private boolean isMine;
    private String userRandomId;
}
