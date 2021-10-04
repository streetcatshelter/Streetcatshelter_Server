package streetcatshelter.discatch.dto.requestDto;

import lombok.Getter;
import lombok.Setter;
import streetcatshelter.discatch.domain.User;

@Getter
@Setter
public class CommentRequestDto {
    private String username;
    private String contents;
}
