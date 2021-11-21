package streetcatshelter.discatch.notification;

import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class NewsController {

    public Map<String, SseEmitter> emitters = new HashMap<>();

    @CrossOrigin
    @RequestMapping(value = "/subscribe", consumes = MediaType.ALL_VALUE)
    public SseEmitter subscribe(@RequestParam String userId) {
        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
        sendInitEvent(sseEmitter);

        emitters.put(userId, sseEmitter);
        sseEmitter.onCompletion(() -> emitters.remove(sseEmitter));
        sseEmitter.onTimeout(() -> emitters.remove(sseEmitter));
        sseEmitter.onError((e) -> emitters.remove(sseEmitter));

        return sseEmitter;
    }

    private void sendInitEvent(SseEmitter sseEmitter) {
        try {
            sseEmitter.send(SseEmitter.event().name("INIT"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @PostMapping(value = "/dispatchEvent")
    public void dispatchEventsToClient(@RequestParam String title, @RequestParam String text, @RequestParam String userId) {

        String eventFormatted = new JSONObject()
                .put("title" , title)
                .put("text" , text).toString();
        SseEmitter sseEmitter = emitters.get(userId);
        if(sseEmitter != null) {
            try {
                sseEmitter.send(SseEmitter.event().name("latestNews").data(eventFormatted));
            } catch (IOException e) {
                emitters.remove(sseEmitter);
            }
        }

        }

}
