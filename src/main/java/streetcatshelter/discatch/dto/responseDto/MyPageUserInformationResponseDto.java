package streetcatshelter.discatch.dto.responseDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import streetcatshelter.discatch.domain.user.domain.UserLevel;

import java.util.List;

@Getter
@Setter
@Builder
public class MyPageUserInformationResponseDto {
    private String nickname;
    private List<String> LocationList;
    private UserLevel userLevel;
    private String profileImageUrl;
    private int cntActivity;
    private String username;
}
