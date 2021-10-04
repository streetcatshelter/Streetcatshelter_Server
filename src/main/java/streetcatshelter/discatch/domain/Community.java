package streetcatshelter.discatch.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private int cntLikeit;

    //조회수
    @Column(nullable = false, columnDefinition = "integer default 0")
    private int cntView;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "community", cascade = {CascadeType.REMOVE})
    private List<CommunityImage> communityImageList = new ArrayList<>();

    @OneToMany(mappedBy = "community", cascade = {CascadeType.REMOVE})
    private List<Comment> commentList = new ArrayList<>();

   /* @OneToMany(mappedBy = "communitylikeit", cascade = {CascadeType.REMOVE})
    private List<CommunityLikeit> communityLikeitList = new ArrayList<>();*/

    public Community(CommunityRequestDto requestDto, User user) {
        this.user = user;
        this.category = requestDto.getCategory();
        this.username = requestDto.getUsername();
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
        this.location = requestDto.getLocation();
    }

    public void update(CommunityRequestDto requestDto, User user) {
        this.user = user;
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
    public void updateCntLikeit(int count) {this.cntLikeit += count;}

}
