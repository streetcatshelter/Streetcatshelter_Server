package streetcatshelter.discatch.domain.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import streetcatshelter.discatch.domain.chat.domain.ChatRoom;
import streetcatshelter.discatch.domain.chat.dto.ChatRoomDto;
import streetcatshelter.discatch.domain.chat.dto.ChatRoomResponseDto;
import streetcatshelter.discatch.domain.chat.repository.ChatMessageRepository;
import streetcatshelter.discatch.domain.chat.repository.ChatRoomRepository;
import streetcatshelter.discatch.domain.chat.service.ChatRoomService;
import streetcatshelter.discatch.domain.chat.service.ChatService;
import streetcatshelter.discatch.domain.oauth.entity.UserPrincipal;
import streetcatshelter.discatch.domain.oauth.token.JwtTokenProvider;
import streetcatshelter.discatch.domain.user.domain.User;
import streetcatshelter.discatch.domain.user.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/chat")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
    private final JwtTokenProvider jwtTokenProvider;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatService chatService;
    private final UserRepository userRepository;
    private final ChatMessageRepository chatMessageRepository;

    //기존에 쓰이던 채팅방 리스트 조회 hash 사용
//    @GetMapping("/rooms")
//    @ResponseBody
//    public List<ChatRoom> room() {
//        List<ChatRoom> chatRooms = chatRoomService.findAllRoom();
//        chatRooms.stream().forEach(room -> room.setUserCount(chatRoomService.getUserCount(room.getRoomId())));
//        return chatRooms;
//    }
    //참여중인 채팅방 조회
    @GetMapping("/rooms")
    public List<ChatRoomResponseDto> profileChange(HttpServletRequest httpServletRequest){
        //토큰에서 사용자 정보 추출
        String token = jwtTokenProvider.resolveToken(httpServletRequest);
        String socialId = jwtTokenProvider.getUserPk(token);
        User user = userRepository.findByUserId(socialId);
        List<ChatRoom> chatRooms = chatRoomRepository.findAllByUser(user);
        List<ChatRoomResponseDto> responseDtoList = new ArrayList<>();
        for(ChatRoom chatRoom : chatRooms) {
            LocalDateTime lastActivity = chatService.lastMessage(chatRoom.getRoomId()).getTime();
            String lastMessage = chatService.lastMessage(chatRoom.getRoomId()).getMessage();
            List<User> chatUsers = chatRoom.getUser();
            chatUsers.remove(user);
            User opponent = chatUsers.get(0);
            String opponentImage;
            if(opponent.getProfileUrl() == null) {
                opponentImage = opponent.getProfileImageUrl();
            } else {
                opponentImage = opponent.getProfileUrl();
            }
            responseDtoList.add(ChatRoomResponseDto.builder()
                    .opponent(opponent.getNickname())
                    .lastActivity(lastActivity)
                    .opponentImage(opponentImage)
                    .roomId(chatRoom.getRoomId())
                    .lastMessage(lastMessage)
                    .build());
        }
        return responseDtoList;
    }


    //채팅방 생성(parameter : roomName, user_email)
    @PostMapping("/create")
    public ChatRoom createRoom(@RequestBody ChatRoomDto chatRoomDto) {
        return chatRoomService.createChatRoom(chatRoomDto);
    }

    //특정 채팅방 입장. 채팅방정보 제공;
    @GetMapping("/enter/{roomId}")
    public ChatRoomResponseDto roomInfo(@PathVariable String roomId, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return chatRoomService.roomInfo(roomId, userPrincipal);
    }

    //채팅방 퇴장 테스트 필요
/*    @Transactional
    @PutMapping("/quit/{roomId}")
    public String quitRoom(@PathVariable String roomId, HttpServletRequest httpServletRequest){
        ChatRoom chatRoom  = chatRoomService.findRoomById(roomId);
        //토큰에서 사용자 정보 추출
        String token = jwtTokenProvider.resolveToken(httpServletRequest);
        String userId = jwtTokenProvider.getUserPk(token);
        User user = userRepository.findByUserId(userId);
        //유저 목록에서 제거
        chatRoom.getUser().remove(user);
        //남아있는 유저가 없을 경우 DB에서 삭제
        if(chatRoom.getUser().isEmpty()){

            chatRoomRepository.delete(chatRoom);
            //redis상 채팅방 정보 삭제
            chatRoomService.deleteChatRoom(roomId);
            //DB에 저장된 메세지 삭제하기
            chatMessageRepository.deleteById(chatRoom.getId());
        }
        else{
            //유저가 남아있다면 나가는 유저 정보를 가져와서 채팅방에 남아있는 인원에게 퇴장 메세지 전송
            chatService.sendChatMessage(ChatMessage.builder().type(ChatMessage.MessageType.QUIT).roomId(roomId).userName(user.getUsername()).build());
        }
        return "채팅방 나가기 완료";
    }*/


}