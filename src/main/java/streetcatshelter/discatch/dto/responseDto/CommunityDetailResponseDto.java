package streetcatshelter.discatch.dto.responseDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import streetcatshelter.discatch.domain.CommunityImage;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@Setter
public class CommunityDetailResponseDto {
    private boolean isLiked;
    private List<CommentResponseDto> commentList;
    private Long communityId;
    private String category;
    private String username;
    private String title;
    private String contents;
    private String location;
    private int cntComment;
    private int cntLikeit;
    private int cntView;
    private List<CommunityImage> communityImageList ;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String nickname;
}
