package streetcatshelter.discatch.dto.responseDto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import streetcatshelter.discatch.domain.Community;

@Getter
public class CommunityResponseDto {

    private Boolean isLiked;
    private Boolean isLast;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Community community;
    private String msg;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object result;

    public CommunityResponseDto(Community community, Boolean isLiked, String msg) {
        this.community = community;
        this.isLiked = isLiked;
        this.msg = msg;
    }

    public CommunityResponseDto(Community community, boolean isLiked) {
        this.community = community;
        this.isLiked = isLiked;
    }

    public CommunityResponseDto(Object result, String msg, Boolean isLast) {
        this.result = result;
        this.msg = msg;
        this.isLast = isLast;
    }
}
