package streetcatshelter.discatch.domain.chat.domain;

import lombok.*;
import streetcatshelter.discatch.domain.TimeStamped;
import streetcatshelter.discatch.domain.user.domain.User;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage extends TimeStamped {

    @Builder
    public ChatMessage(MessageType type, String roomId, User sender, String message) {
        this.type = type;
        this.roomId = roomId;
        this.sender = sender;
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

    @ManyToOne
    private User sender;

    @Column
    private String message; // 메시지

    @Column
    private String time; // 메시지
}