package streetcatshelter.discatch.domain.community.dto;

import lombok.Getter;

@Getter
public class CommunityResponseDto {

    private Boolean isLiked;
    private String title;
    private String nickname;
    private String createdAt;
    private int cntComment;
    private int cntLikeit;
    private int cntView;
    private String profileImageUrl;
    private Long communityId;
    private String username;

    public CommunityResponseDto(String title, boolean isLiked, String nickname, String createdAt, int cntComment, int cntLikeit, int cntView, String profileImageUrl, Long communityId, String username) {
        this.cntComment = cntComment;
        this.isLiked = isLiked;
        this.nickname = nickname;
        this.title = title;
        this.createdAt = createdAt;
        this.cntLikeit = cntLikeit;
        this.cntView = cntView;
        this.profileImageUrl = profileImageUrl;
        this.communityId = communityId;
        this.username = username;
    }
}
