package streetcatshelter.discatch.domain.user.dto;

import lombok.Getter;

import java.util.List;


@Getter
public class UserInformationRequestDto {

    private String nickname;
    private List<String> location;
    private String profileUrl;

}
