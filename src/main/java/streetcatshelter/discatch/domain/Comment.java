package streetcatshelter.discatch.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import streetcatshelter.discatch.domain.cat.domain.Cat;
import streetcatshelter.discatch.domain.cat.domain.CatDetail;
import streetcatshelter.discatch.domain.user.domain.User;
import streetcatshelter.discatch.dto.requestDto.CommentRequestDto;



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

    @JoinColumn(name = "COMMUNITY_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Community community;

    @JoinColumn(name = "CAT_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Cat cat;

    @JoinColumn(name = "CATDETAIL_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private CatDetail catDetail;

    @JsonIgnore
    @JoinColumn(name = "USER_USER_SEQ")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;



    public Comment(Community community, CommentRequestDto requestDto, User user) {
        this.community = community;
        this.contents = requestDto.getContents();
        this.user = user;
    }

    public Comment(CatDetail catDetail,CommentRequestDto requestDto,User user) {
        this.user = user;
        this.catDetail = catDetail;
        this.contents = requestDto.getContents();
    }

    public Comment(Cat cat,CommentRequestDto requestDto,User user) {
        this.user = user;
        this.cat = cat;
        this.contents = requestDto.getContents();
    }

    public void update(CommentRequestDto requestDto) {
        this.contents = requestDto.getContents();
    }
}