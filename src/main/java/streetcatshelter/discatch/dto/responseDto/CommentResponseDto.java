package streetcatshelter.discatch.dto.responseDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class CommentResponseDto {

    private Long commentId;
    private String contents;
    private Long userId;
    private String username;
    private String nickname;
    private String profileImageUrl;
    private String createdAt;
    private String modifiedAt;
    private Boolean isMine;
}
