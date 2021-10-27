package streetcatshelter.discatch.domain.community.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import streetcatshelter.discatch.domain.community.domain.CommunityImage;
import streetcatshelter.discatch.dto.responseDto.CommentResponseDto;

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
    private String profileImageUrl;
    private String location;
    private int cntComment;
    private int cntLikeit;
    private int cntView;
    private List<CommunityImage> communityImageList ;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String nickname;
}
