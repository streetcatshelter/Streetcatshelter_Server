package streetcatshelter.discatch.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import streetcatshelter.discatch.dto.CommentRequestDto;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comment")
public class Comment  extends TimeStamped { // 생성,수정 시간을 자동으로 만들어줍니다.

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private String username;

    @JoinColumn(name = "COMMUNITY_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Community community;

    public Comment(Community community, CommentRequestDto requestDto) {
        this.community = community;
        this.contents = requestDto.getContents();
        this.username = requestDto.getUsername();
    }
    public void update(CommentRequestDto requestDto) {
        this.contents = requestDto.getContents();
    }
}