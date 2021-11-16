package streetcatshelter.discatch.domain.chat.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import streetcatshelter.discatch.domain.TimeStamped;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class ChatMessage extends TimeStamped {

    public ChatMessage() {
    }

    @Builder
    public ChatMessage(MessageType type, String roomId, String userName, String message) {
        this.type = type;
        this.roomId = roomId;
        this.userName = userName;
        this.message = message;
    }

    // 메시지 타입 : 입장, 퇴장, 채팅
    public enum MessageType {
        ENTER, QUIT, TALK
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private MessageType type; // 메시지 타입

    @Column
    private String roomId; // 방번호

    @Column
    private String userName; // 메시지 보낸사람

    @Column
    private String message; // 메시지
}