package streetcatshelter.discatch.domain.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Service;
import streetcatshelter.discatch.domain.chat.domain.ChatRoom;
import streetcatshelter.discatch.domain.chat.dto.ChatRoomDto;
import streetcatshelter.discatch.domain.chat.dto.ChatRoomResponseDto;
import streetcatshelter.discatch.domain.chat.repository.ChatRoomRepository;
import streetcatshelter.discatch.domain.oauth.entity.UserPrincipal;
import streetcatshelter.discatch.domain.user.domain.User;
import streetcatshelter.discatch.domain.user.repository.UserRepository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class ChatRoomService {
    // Redis CacheKeys


    @Resource(name = "redisTemplate")
    private HashOperations<String, String, String> hashOpsEnterInfo;
    @Resource(name = "redisTemplate")
    private HashOperations<String, String, String> hashOpsUserInfo;

    private final ChatRoomRepository chatRoomRepository;
    private final ChatService chatService;
    private final UserRepository userRepository;

    public static final String ENTER_INFO = "ENTER_INFO";
    public static final String USER_INFO = "USER_INFO";

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
    public ChatRoom createChatRoom(ChatRoomDto chatRoomDto, UserPrincipal userPrincipal) {
        String userSeqSum = makeUserSeqSum(chatRoomDto);
        User user = userPrincipal.getUser();
        if(chatRoomRepository.findByUserSeqSum(userSeqSum) == null) {
            ChatRoom chatRoom = ChatRoom.create(userSeqSum);
            for(String nickname : chatRoomDto.getChatUser()){
                User tempUser = userRepository.findByNickname(nickname);
                chatRoom.getUser().add(tempUser);
            }
            chatRoomRepository.save(chatRoom);
            return chatRoom;
        } else {
            ChatRoom chatRoom = chatRoomRepository.findByUserSeqSum(userSeqSum);
            chatRoom.getUser().add(user);
            chatRoomRepository.save(chatRoom);
            return chatRoomRepository.findByUserSeqSum(userSeqSum);
        }
    }


    public ChatRoomResponseDto roomInfo(String roomId, UserPrincipal userPrincipal) {
        ChatRoom chatRoom = chatRoomRepository.findByRoomId(roomId);
        User user = userPrincipal.getUser();
        User opponent;
        String opponentImage;
        String nickname;
        String userRandomId;

        if(chatRoom.getUser().size() == 2) {
            if (chatRoom.getUser().get(0).equals(user)) {
                opponent = chatRoom.getUser().get(1);
            } else {
                opponent = chatRoom.getUser().get(0);
            }
            if(opponent.getProfileUrl() == null) {
                opponentImage = opponent.getProfileImageUrl();
            } else {
                opponentImage = opponent.getProfileUrl();
            }
            nickname = opponent.getNickname();
            userRandomId = opponent.getUserRandomId();
        } else {
            opponentImage = null;
            nickname = null;
            userRandomId = null;
        }

        String lastActivity;
        if(chatService.lastMessage(roomId) != null) {
            lastActivity = chatService.lastMessage(roomId).getTime();
        } else {
            lastActivity = null;
        }

        return ChatRoomResponseDto.builder()
                .opponent(nickname)
                .opponentImage(opponentImage)
                .lastActivity(lastActivity)
                .roomId(chatRoom.getRoomId())
                .userRandomId(userRandomId)
                .build();
    }

    // redis 에 입장정보로 sessionId 와 roomId를 저장하고 해단 sessionId 와 토큰에서 받아온 userId를 저장함
    public void setUserEnterInfo(String sessionId, String roomId, Long userId) {
        hashOpsEnterInfo.put(ENTER_INFO, sessionId, roomId);
        hashOpsUserInfo.put(USER_INFO, sessionId, Long.toString(userId));
    }

    // redis 에 저장했던 sessionId 로 roomId를 리턴함
    public String getUserEnterRoomId(String sessionId) {
        return hashOpsEnterInfo.get(ENTER_INFO, sessionId);
    }

    // 유저가 나갈때 redis 에 저장했던 해당 세션 / 유저의 정보를 삭제함
    public void removeUserEnterInfo(String sessionId) {
        hashOpsEnterInfo.delete(ENTER_INFO, sessionId);
        hashOpsUserInfo.delete(USER_INFO, sessionId);
    }

    // redis 에 저장했던 sessionId 로 userId 를 얻어오고 해당 userId 로 User 객체를 찾아 리턴함
    public User chkSessionUser(String sessionId) {
        Long userId = Long.parseLong(Objects.requireNonNull(hashOpsUserInfo.get(USER_INFO, sessionId)));
        return userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자"));
    }
}