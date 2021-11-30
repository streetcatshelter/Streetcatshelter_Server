package streetcatshelter.discatch.alarm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Alarm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userRandomId;

    @Column(nullable = false)
    private String target;

    @Column(nullable = false)
    private String what;

    public Alarm(AlarmRequestDto requestDto) {
        this.target = requestDto.getTarget();
        this.what = requestDto.getWhat();
        this.userRandomId = requestDto.getUserRandomId();
    }
}
