package streetcatshelter.discatch.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import streetcatshelter.discatch.domain.user.domain.UserLevel;

import java.util.List;

@Builder
@Getter
@Setter
public class UserInfoResponseDto {
    private String nickname;
    private UserLevel userLevel;
    private int score;
    private String lastActivity;
    private List<String> location;
    private List<String> cat;
    private int catNum;
    private int postNum;
    private int commentNum;
    private int likedNum;
}
