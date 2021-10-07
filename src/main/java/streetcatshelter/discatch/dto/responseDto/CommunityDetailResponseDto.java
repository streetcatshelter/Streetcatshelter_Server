package streetcatshelter.discatch.dto.responseDto;

import lombok.Getter;
import streetcatshelter.discatch.domain.Community;

@Getter

public class CommunityDetailResponseDto {
    private Community community;
    private boolean isLiked;

    public CommunityDetailResponseDto(Community community, boolean isLiked) {
        this.community = community;
        this.isLiked = isLiked;
    }
}
