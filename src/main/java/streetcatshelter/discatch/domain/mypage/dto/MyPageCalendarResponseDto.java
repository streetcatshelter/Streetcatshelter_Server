package streetcatshelter.discatch.domain.mypage.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@Setter
public class MyPageCalendarResponseDto {
    private Long catId;
    private boolean water;
    private boolean food;
    private boolean snack;
    private String catName;
    private String location;
    private String catImage;
}
