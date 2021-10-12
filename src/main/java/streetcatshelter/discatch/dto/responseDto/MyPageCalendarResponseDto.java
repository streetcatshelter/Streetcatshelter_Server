package streetcatshelter.discatch.dto.responseDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Builder
@Setter
public class MyPageCalendarResponseDto {
    private Long id;
    private boolean water;
    private boolean food;
    private boolean snack;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
