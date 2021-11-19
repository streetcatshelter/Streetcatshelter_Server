package streetcatshelter.discatch.domain.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import streetcatshelter.discatch.domain.chat.domain.ChatRoom;
import streetcatshelter.discatch.domain.chat.dto.ChatRoomDto;
import streetcatshelter.discatch.domain.chat.dto.ChatRoomResponseDto;
import streetcatshelter.discatch.domain.chat.repository.ChatRoomRepository;
import streetcatshelter.discatch.domain.oauth.entity.UserPrincipal;
import streetcatshelter.discatch.domain.user.domain.User;
import streetcatshelter.discatch.domain.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ChatRoomService {
    // Redis CacheKeys
    private final ChatRoomRepository chatRoomRepository;
    private final ChatService chatService;
    private final UserRepository userRepository;

    public String makeUserSeqSum(ChatRoomDto chatRoomDto) {
        List<String> chatUsers = chatRoomDto.getChatUser();
        String userSeqSum;
        ArrayList<Long> userSeq = new ArrayList<>();
        for(String chatUser : chatUsers) {
            userSeq.add(userRepository.findByNickname(chatUser).getUserSeq());
        }
        Collections.sort(userSeq);
        userSeqSum = userSeq.get(0) + "Ian" + userSeq.get(1);
        return userSeqSum;
    }
    // 채팅방 생성 : 서버간 채팅방 공유를 위해 redis hash에 저장한다. -> 이것으로 채팅방은 지워지지 않음
    public ChatRoom createChatRoom(ChatRoomDto chatRoomDto) {
        String userSeqSum = makeUserSeqSum(chatRoomDto);
        if(chatRoomRepository.findByUserSeqSum(userSeqSum) == null) {
            ChatRoom chatRoom = ChatRoom.create(userSeqSum);
            for(String nickname : chatRoomDto.getChatUser()){
                User tempUser = userRepository.findByNickname(nickname);
                chatRoom.getUser().add(tempUser);
            }
            chatRoomRepository.save(chatRoom);
            return chatRoom;
        } else {
            return chatRoomRepository.findByUserSeqSum(userSeqSum);
        }
    }


    public ChatRoomResponseDto roomInfo(String roomId, UserPrincipal userPrincipal) {
        ChatRoom chatRoom = chatRoomRepository.findByRoomId(roomId);
        User user = userPrincipal.getUser();
        User opponent;
        if(chatRoom.getUser().get(0).equals(user)) {
            opponent = chatRoom.getUser().get(1);
        } else {
            opponent = chatRoom.getUser().get(0);
        }

        String opponentImage;
        if(opponent.getProfileUrl() == null) {
            opponentImage = opponent.getProfileImageUrl();
        } else {
            opponentImage = opponent.getProfileUrl();
        }

        String lastActivity;
        if(chatService.lastMessage(roomId) != null) {
            lastActivity = chatService.lastMessage(roomId).getTime();
        } else {
            lastActivity = null;
        }

        return ChatRoomResponseDto.builder()
                .opponent(opponent.getNickname())
                .opponentImage(opponentImage)
                .lastActivity(lastActivity)
                .roomId(chatRoom.getRoomId())
                .build();
    }
}