package streetcatshelter.discatch.domain.chat.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ChatMessageResponseDto {
    private String message;
    private String sender;
    private LocalDateTime time;
    private boolean isMine;
}
