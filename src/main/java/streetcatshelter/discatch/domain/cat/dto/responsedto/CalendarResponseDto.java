package streetcatshelter.discatch.domain.cat.dto.responsedto;


import lombok.Data;
import lombok.NoArgsConstructor;
import streetcatshelter.discatch.domain.cat.domain.CatCalender;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class CalendarResponseDto {

    private LocalDate date;
    private boolean food = false;
    private boolean water = false;
    private boolean snack = false;

    public CalendarResponseDto(CatCalender catCalender){
        this.date = catCalender.getLocalDate();
        this.food = catCalender.isFood();
        this.water = catCalender.isWater();
        this.snack = catCalender.isSnack();
    }
}
