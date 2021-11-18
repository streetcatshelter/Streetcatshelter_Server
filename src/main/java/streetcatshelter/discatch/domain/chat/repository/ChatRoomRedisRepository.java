package streetcatshelter.discatch.domain.chat.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import streetcatshelter.discatch.domain.chat.domain.ChatRoom;
import streetcatshelter.discatch.domain.user.repository.UserRepository;

import javax.annotation.Resource;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ChatRoomRedisRepository {
    // Redis CacheKeys
    private static final String CHAT_ROOMS = "CHAT_ROOM"; // 채팅룸 저장
    public static final String ENTER_INFO = "ENTER_INFO"; // 채팅룸에 입장한 클라이언트의 sessionId와 채팅룸 id를 맵핑한 정보 저장
    private final UserRepository userRepository;

    @Resource(name = "redisTemplate")
    private HashOperations<String, String, ChatRoom> hashOpsChatRoom;
    @Resource(name = "redisTemplate")
    private HashOperations<String, String, String> hashOpsEnterInfo;
    @Resource(name = "redisTemplate")
    private ValueOperations<String, String> valueOps;

    // 모든 채팅방 조회
    public List<ChatRoom> findAllRoom() {
        return hashOpsChatRoom.values(CHAT_ROOMS);
    }

    // 특정 채팅방 조회
    public ChatRoom findRoomById(String id) {
        return hashOpsChatRoom.get(CHAT_ROOMS, id);
    }

    // 채팅방 생성 : 서버간 채팅방 공유를 위해 redis hash에 저장한다.
/*    public ChatRoom createChatRoom(ChatRoomDto chatRoomDto) {
        List<String> chatUsers = chatRoomDto.getChatUser();
        List<User> users = new ArrayList<>();
        for(String chatUser : chatUsers ) {
            users.add(userRepository.findByNickname(chatUser));
        }
        ChatRoom chatRoom = ChatRoom.create(users);
        hashOpsChatRoom.put(CHAT_ROOMS, chatRoom.getUser().stream().sorted().toString(), chatRoom);
        return chatRoom;
    }*/

    // 유저가 입장한 채팅방ID와 유저 세션ID 맵핑 정보 저장
    public void setUserEnterInfo(String sessionId, String roomId) {
        hashOpsEnterInfo.put(ENTER_INFO, sessionId, roomId);
    }

    // 유저 세션으로 입장해 있는 채팅방 ID 조회
    public String getUserEnterRoomId(String sessionId) {
        return hashOpsEnterInfo.get(ENTER_INFO, sessionId);
    }

    // 유저 세션정보와 맵핑된 채팅방ID 삭제
    public void removeUserEnterInfo(String sessionId) {
        hashOpsEnterInfo.delete(ENTER_INFO, sessionId);
    }
}