package streetcatshelter.discatch.dto.responseDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import streetcatshelter.discatch.domain.User;

@Getter
@Setter
@Builder
public class CommentResponseDto {
    private Long commentId;
    private String contents;
    private Long userId;
    private String username;
    private String profileImageUrl;

}
