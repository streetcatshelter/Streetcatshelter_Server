package streetcatshelter.discatch.alarm;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AlarmService {

    private final AlarmRepository alarmRepository;

    public void createAlarm(AlarmRequestDto requestDto) {
        Alarm alarm = new Alarm(requestDto);
        alarmRepository.save(alarm);
    }
}
