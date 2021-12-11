package streetcatshelter.discatch.domain.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import streetcatshelter.discatch.domain.chat.domain.ChatMessage;
import streetcatshelter.discatch.domain.chat.dto.ChatMessageResponseDto;
import streetcatshelter.discatch.domain.chat.service.ChatService;
import streetcatshelter.discatch.domain.oauth.entity.UserPrincipal;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ChatController {//ChatService에서 입/퇴장을 처리하기 때문에 간소

    private final ChatService chatService;

    /**
     * websocket "/pub/api/chat/message"로 들어오는 메시징을 처리한다.
     */
    //채팅방에 접속하면서 채팅방에 저장된 메세지를 불러오는 api
    @GetMapping("/api/chat/message/{roomId}")
    @ResponseBody
    public List<ChatMessageResponseDto> loadMessage(@PathVariable String roomId, @AuthenticationPrincipal UserPrincipal userPrincipal, @RequestParam int page, @RequestParam int size) {
        Pageable pageable = PageRequest.of(page -1 , size);
        return chatService.loadMessage(roomId, userPrincipal, pageable);
    }

    //가장 최근에 채팅방에서 전송된 메세지 확인
    @GetMapping("/api/chat/message/last/{roomId}")
    @ResponseBody
    public ChatMessageResponseDto lastMessage(@PathVariable String roomId){
        return chatService.lastMessage(roomId);
    }


    @MessageMapping("/api/chat/message") // 웹소켓으로 들어오는 메시지 발행 처리 -> 클라이언트에서는 /pub/api/chat/message로 발행 요청
    public void message(@RequestBody ChatMessage message, @Header("token") String token) {
        chatService.message(message, token);
    }
}