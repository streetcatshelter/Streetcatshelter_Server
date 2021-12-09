package streetcatshelter.discatch.alarm;

import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import streetcatshelter.discatch.domain.oauth.entity.UserPrincipal;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//SseEmitter에 로그인 한 유저들을 모두 다 등록해줘야한다.
@RestController
@RequiredArgsConstructor
public class AlarmController {

    private final AlarmService alarmService;
    private final AlarmRepository alarmRepository;

    public Map<String, SseEmitter> emitters = new HashMap<>();
    //로그인 유저의 정보를 가지고 SseEmitter에 한명씩 등록해주는 api
    @CrossOrigin
    @Transactional
    @RequestMapping(value = "/alarm/init", consumes = MediaType.ALL_VALUE)
    public SseEmitter subscribe(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
        sendInitEvent(sseEmitter, userPrincipal);
        String userRandomId = userPrincipal.getUser().getUserRandomId();

        emitters.put(userRandomId, sseEmitter);
        sseEmitter.onCompletion(() -> emitters.remove(sseEmitter));
        sseEmitter.onTimeout(() -> emitters.remove(sseEmitter));
        sseEmitter.onError((e) -> emitters.remove(sseEmitter));

        return sseEmitter;
    }

    //연결이 성공적으로 되었을시에 알람기능
    public void sendInitEvent(SseEmitter sseEmitter, UserPrincipal userPrincipal) {
        String userRandomId = userPrincipal.getUser().getUserRandomId();
        try {
            sseEmitter.send(SseEmitter.event().name("INIT").data(alarmRepository.findAllByUserRandomId(userRandomId)));
            alarmRepository.deleteAllByUserRandomId(userRandomId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping(value = "/dispatchEvent")
    public void alarmToClient(@RequestParam AlarmRequestDto requestDto) {
        String userRandomId = requestDto.getUserRandomId();
        String eventFormatted = new JSONObject()
                .put("target" , requestDto.getTarget())
                .put("what" , requestDto.getWhat()).toString();
        SseEmitter sseEmitter = emitters.get(userRandomId);
        if(sseEmitter != null) {
            try {
                sseEmitter.send(SseEmitter.event().name("alarms").data(eventFormatted));
            } catch (IOException e) {
                emitters.remove(sseEmitter);
                alarmService.createAlarm(requestDto);
            }
        } else {
            //로그인을 하지않아서 sseEmtter에 없는경우네는 alarmRepository를 이용해서save해놔야한다.
            alarmService.createAlarm(requestDto);

        }
    }

}
