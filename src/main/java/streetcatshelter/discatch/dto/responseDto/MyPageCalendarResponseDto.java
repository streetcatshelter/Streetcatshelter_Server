package streetcatshelter.discatch.dto.responseDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@Setter
public class MyPageCalendarResponseDto {
    private Long catId;
    private Long catDetailId;
    private boolean water;
    private boolean food;
    private boolean snack;
    private String createdAt;
    private String modifiedAt;
    private String catName;
}
