package streetcatshelter.discatch.dto.responseDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import streetcatshelter.discatch.domain.UserLevel;
import streetcatshelter.discatch.domain.UserLocation;

import java.util.List;

@Getter
@Setter
@Builder
public class MyPageUserInformationResponseDto {
    private String nickname;
    private List<UserLocation> userLocationList;
    private UserLevel userLevel;
    private String profileImageUrl;
    private int cntActivity;
    private String username;
}
