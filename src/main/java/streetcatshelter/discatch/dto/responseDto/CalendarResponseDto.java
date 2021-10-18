package streetcatshelter.discatch.dto.responseDto;


import lombok.Data;
import lombok.NoArgsConstructor;
import streetcatshelter.discatch.domain.CatCalender;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class CalendarResponseDto {

    private LocalDate localDate;
    private boolean food = false;
    private boolean water = false;
    private boolean snack = false;

    public void update(CatCalender catCalender) {
        this.food = catCalender.isFood();
        this.snack = catCalender.isSnack();
        this.water = catCalender.isWater();
    }
}
