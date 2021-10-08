package streetcatshelter.discatch.dto.responseDto;

import lombok.Getter;

@Getter
public class MyPageUserInformationResponseDto {
    private String msg;

    public MyPageUserInformationResponseDto(String msg) {
        this.msg = msg;
    }
}
