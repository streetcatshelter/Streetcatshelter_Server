package streetcatshelter.discatch.dto.responseDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import streetcatshelter.discatch.domain.User;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class CommentResponseDto {
    private LocalDateTime createdAt;
    private Long commentId;
    private String contents;
    private Long userId;
    private String username;
    private String profileImageUrl;

}
