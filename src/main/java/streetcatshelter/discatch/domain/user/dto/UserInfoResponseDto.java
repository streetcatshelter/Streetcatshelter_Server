package streetcatshelter.discatch.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import streetcatshelter.discatch.domain.user.domain.UserLevel;

import java.util.List;
import java.util.Map;

@Builder
@Getter
@Setter
public class UserInfoResponseDto {
    private String nickname;
    private UserLevel userLevel;
    private int score;
    private String lastActivity;
    private List<String> location;
    private List<Map<String, String>> cat;
    private int catNum;
    private int postNum;
    private int commentNum;
    private int likedNum;
    private String userRandomId;
    private String profileImageUrl;
    private int scoreLeft;
}
