package streetcatshelter.discatch.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import streetcatshelter.discatch.dto.CommunityRequestDto;

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

/*    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;*/

    @OneToMany(mappedBy = "community", cascade = {CascadeType.REMOVE})
    private List<CommunityImage> communityImageList = new ArrayList<>();


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
}
