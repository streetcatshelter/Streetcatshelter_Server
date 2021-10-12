package streetcatshelter.discatch.dto.responseDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import streetcatshelter.discatch.domain.UserLevel;

@Getter
@Setter
@Builder
public class MyPageUserInformationResponseDto {
    private String nickname;
    private String location;
    private String location2;
    private String location3;
    private UserLevel userLevel;
    private String profileImageUrl;
    private int cntActivity;
}
