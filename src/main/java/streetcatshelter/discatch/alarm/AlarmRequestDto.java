package streetcatshelter.discatch.alarm;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlarmRequestDto {
    private String target;
    private String what;
    private String userRandomId;
}
