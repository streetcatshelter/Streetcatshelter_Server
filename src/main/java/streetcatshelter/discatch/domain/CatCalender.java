package streetcatshelter.discatch.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import streetcatshelter.discatch.dto.requestDto.CatDetailRequestDto;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
public class CatCalender {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private LocalDate localDate;

    private boolean food;

    private boolean water;

    private boolean snack;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CAT_ID")
    private Cat cat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_USER_SEQ")
    private User user;

    public CatCalender(LocalDate localDate, boolean food, boolean snack, boolean water, Cat cat , User user) {
        this.localDate = localDate;
        this.food = food;
        this.snack = snack;
        this.water = water;
        this.cat = cat;
        this.user = user;
    }

    public void update(CatDetailRequestDto requestDto) {
        if(requestDto.isWater()){
            this.water = requestDto.isWater();
        }
        if(requestDto.isFood()){
            this.water = requestDto.isFood();
        }
        if(requestDto.isSnack()){
            this.snack = requestDto.isSnack();
        }

    }
}
