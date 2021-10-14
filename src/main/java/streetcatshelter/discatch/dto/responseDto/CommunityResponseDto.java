package streetcatshelter.discatch.dto.responseDto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommunityResponseDto {

    private Boolean isLiked;
    private String title;
    private String nickname;
    private LocalDateTime createdAt;
    private int cntComment;
    private int cntLikeit;
    private int cntView;
    private String profileUrl;
    private Long communityId;
    private String username;

    public CommunityResponseDto(String title, boolean isLiked, String nickname, LocalDateTime createdAt, int cntComment, int cntLikeit, int cntView, String profileUrl, Long communityId, String username) {
        this.cntComment = cntComment;
        this.isLiked = isLiked;
        this.nickname = nickname;
        this.title = title;
        this.createdAt = createdAt;
        this.cntLikeit = cntLikeit;
        this.cntView = cntView;
        this.profileUrl = profileUrl;
        this.communityId = communityId;
        this.username = username;
    }
}
