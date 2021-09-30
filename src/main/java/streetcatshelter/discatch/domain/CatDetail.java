package streetcatshelter.discatch.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import streetcatshelter.discatch.dto.CatDetailRequestDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CatDetail extends TimeStamped{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private boolean water;

    @Column(nullable = false)
    private boolean food;

    @Column(nullable = false)
    private boolean snack;

    @Column(nullable = false)
    private String catDetailImage;

    @Column(nullable = false)
    private String diary;

    // 위도 경도 따로 db를 만들지 생각해보자..
    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;

    @JoinColumn(name = "USER_USER_SEQ")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "catDetail", cascade = {CascadeType.REMOVE})
    private List<Comment> comments = new ArrayList<>();

    @JoinColumn(name = "CAT_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Cat cat;

    public CatDetail(CatDetailRequestDto requestDto, Cat cat, User user) {
        this.cat = cat;
        this.food = requestDto.isFood();
        this.snack = requestDto.isSnack();
        this.water = requestDto.isWater();
        this.catDetailImage = requestDto.getCatDetailImage();
        this.diary = requestDto.getDiary();
        this.user = user;
    }

/*    @OneToMany(mappedBy = "CatDetail", cascade = {CascadeType.REMOVE})
    private List<CatTag> catTagList  = new ArrayList<>();*/

}
