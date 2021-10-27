package streetcatshelter.discatch.domain.mypage.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class MyPageAllCalendarResponseDto {
    private boolean water;
    private boolean snack;
    private boolean food;
    //private String date;
    private LocalDate date;
}
