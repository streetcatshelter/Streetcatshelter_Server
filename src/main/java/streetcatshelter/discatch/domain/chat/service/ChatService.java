package streetcatshelter.discatch.domain.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;
import streetcatshelter.discatch.domain.chat.domain.ChatMessage;
import streetcatshelter.discatch.domain.chat.dto.ChatMessageResponseDto;
import streetcatshelter.discatch.domain.chat.repository.ChatMessageRepository;
import streetcatshelter.discatch.domain.oauth.entity.UserPrincipal;
import streetcatshelter.discatch.domain.oauth.token.JwtTokenProvider;
import streetcatshelter.discatch.domain.user.domain.User;
import streetcatshelter.discatch.domain.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ChatService { //입장, 퇴장 처리

    private final ChannelTopic channelTopic;
    private final RedisTemplate redisTemplate;
    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * destination정보에서 roomId 추출
     */
    public String getRoomId(String destination) {
        int lastIndex = destination.lastIndexOf('/');
        if (lastIndex != -1)
            return destination.substring(lastIndex + 1);
        else
            return "";
    }

    /**
     * 채팅방에 메시지 발송
     */
    public void sendChatMessage(ChatMessage message) {
        if (ChatMessage.MessageType.ENTER.equals(message.getType())) {
            message.setMessage(message.getSender().getNickname() + "님이 방에 입장했습니다.");
        } else if (ChatMessage.MessageType.QUIT.equals(message.getType())) {
            message.setMessage(message.getSender().getNickname() + "님이 자리를 비웠어요.");
        }
        redisTemplate.convertAndSend(channelTopic.getTopic(), message);
    }

    public List<ChatMessageResponseDto> loadMessage(String roomId, UserPrincipal userPrincipal) {
        List<ChatMessage> messages = chatMessageRepository.findAllByRoomIdOrderByCreatedAtAsc(roomId);
        List<ChatMessageResponseDto> responseDtoList = new ArrayList<>();
        for(ChatMessage chatMessage : messages) {

/*            Date date = java.sql.Timestamp.valueOf(chatMessage.getCreatedAt());
            String time = Time.calculateTime(date);*/

            User user = userPrincipal.getUser();
            boolean isMine;
            if(user.getNickname().equals(chatMessage.getSender().getNickname())) {
                isMine = true;
            } else {
                isMine = false;
            }

            responseDtoList.add(ChatMessageResponseDto.builder()
                    .message(chatMessage.getMessage())
                    .time(chatMessage.getTime())
                    .sender(chatMessage.getSender().getNickname())
                    .isMine(isMine)
                    .build());
        }
        return responseDtoList;
    }

    public void message(ChatMessage message, String token) {
        System.out.println("채팅메세지 서비스에 들어옴");
        String userId = jwtTokenProvider.getUserPk(token); //회원의 대화명을 가져와 token 유효성 체크
        User member = userRepository.findByUserId(userId);
        System.out.println("유저를 디비에서 찾아옴");
        String nickname = member.getNickname();
        // 헤더에서 토큰을 읽어 로그인 회원 정보로 대화명 설정
        message.setSender(member);
        message.setTime(String.valueOf(LocalDateTime.now()));
        System.out.println("토큰 유효성 확인 완료, 해당 닉네임 : "+ nickname);
        // 채팅방 인원수 세팅
        System.out.println(message);
        System.out.println("DB 저장 완료");
        // Websocket에 발행된 메시지를 redis로 발행(publish)
        sendChatMessage(message); // 메서드 일원화
        System.out.println("메세지 송부 요청 완료");
    }

    public ChatMessageResponseDto lastMessage(String roomId) {
        if(chatMessageRepository.findFirstByRoomIdOrderByCreatedAtDesc(roomId) == null) {
            return ChatMessageResponseDto.builder()
                    .message("메세지가없어요")
                    .isMine(false)
                    .sender("메세지가없어요")
                    .build();
        }
        ChatMessage chatMessage = chatMessageRepository.findFirstByRoomIdOrderByCreatedAtDesc(roomId);
/*        Date date = java.sql.Timestamp.valueOf(chatMessage.getCreatedAt());
        System.out.println(date);
        String time = Time.calculateTime(date);*/
        return ChatMessageResponseDto.builder().message(chatMessage.getMessage())
                .sender(chatMessage.getMessage())
                .time(chatMessage.getTime())
                .sender(chatMessage.getSender().getNickname())
                .build();
    }
}