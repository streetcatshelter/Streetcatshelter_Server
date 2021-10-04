package streetcatshelter.discatch.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import streetcatshelter.discatch.dto.requestDto.CommunityRequestDto;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "community")
public class Community extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false, columnDefinition = "integer default 0")
    private int cntComment;

    @Column(nullable = false, columnDefinition = "integer default 0")
    private int cntLikeIt;

    //조회수
    @Column(nullable = false, columnDefinition = "integer default 0")
    private int cntView;

/*    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;*/

    @OneToMany(mappedBy = "community", cascade = {CascadeType.REMOVE})
    private List<CommunityImage> communityImageList = new ArrayList<>();

    @OneToMany(mappedBy = "community", cascade = {CascadeType.REMOVE})
    private List<Comment> commentList = new ArrayList<>();

    public Community(CommunityRequestDto requestDto) {
        this.category = requestDto.getCategory();
        this.username = requestDto.getUsername();
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
        this.location = requestDto.getLocation();
    }

    public void update(CommunityRequestDto requestDto) {
        this.category = requestDto.getCategory();
        this.username = requestDto.getUsername();
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
        this.location = requestDto.getLocation();
    }

    public void addCommunityImageList(List<CommunityImage> communityImageList) {
        this.communityImageList = communityImageList;
    }

    public void updateCntView(int cntView) {
        this.cntView = cntView;
    }
    public void updateCntComment(int cntComment) {
        this.cntComment = cntComment;
    }

}
