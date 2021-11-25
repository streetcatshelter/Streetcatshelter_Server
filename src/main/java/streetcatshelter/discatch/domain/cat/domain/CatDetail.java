package streetcatshelter.discatch.domain.cat.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import streetcatshelter.discatch.domain.Comment;
import streetcatshelter.discatch.domain.Liked;
import streetcatshelter.discatch.domain.TimeStamped;
import streetcatshelter.discatch.domain.cat.dto.requestdto.CatDetailUpdateRequestDto;
import streetcatshelter.discatch.domain.user.domain.User;
import streetcatshelter.discatch.domain.cat.dto.requestdto.CatDetailRequestDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CatDetail extends TimeStamped {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private boolean water;

    @Column(nullable = false)
    private boolean food;

    @Column(nullable = false)
    private boolean snack;

    @Column
    private Long likeCnt;

    @Column
    private Long viewCnt;

    @Column
    private Long commentCnt;

    @OneToMany(mappedBy = "catDetail",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CatImage> catImages = new ArrayList<>();

    @Column(nullable = false)
    private String diary;

    // 위도 경도 따로 db를 만들지 생각해보자..
    @Column
    private double latitude;

    @Column
    private double longitude;

    @JoinColumn(name = "USER_USER_SEQ")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "catDetail", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "catDetail", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Liked> likeds = new ArrayList<>();

    @OneToMany(mappedBy = "catDetail",cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<CatTag> catTags = new ArrayList<>();

    @JoinColumn(name = "CAT_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Cat cat;

//    public CatDetail(CatDetailRequestDto requestDto, Cat cat, User user) {
//
//        this.cat = cat;
//        this.food = requestDto.isFood();
//        this.snack = requestDto.isSnack();
//        this.water = requestDto.isWater();
//        this.diary = requestDto.getDiary();
//        this.user = user;
//    }

    public CatDetail(CatDetailRequestDto requestDto, Cat cat, User user){

        this.cat = cat;
        this.food = requestDto.isFood();
        this.snack = requestDto.isSnack();
        this.water = requestDto.isWater();
        this.diary = requestDto.getDiary();
        this.user = user;
        for(String tag : requestDto.getCatTags()){
            this.catTags.add(CatTag.createTagByCatDetail(cat,this,tag));
        }
        for(String image : requestDto.getCatImages()){
            this.catImages.add(new CatImage(cat,this,image));
        }
        this.commentCnt = 0L;
        this.viewCnt = 0L;
        this.likeCnt = 0L;
    }

    public void updateView() {
        this.viewCnt += 1;
    }

    public void updateCommentCnt() {
        this.commentCnt += 1;
    }

    public Long updateLikeCnt(Long cnt) {
        this.likeCnt += cnt;
        return this.likeCnt;
    }

    public void updateCatDetail(CatDetailUpdateRequestDto catDetailUpdateRequestDto, List<CatTag> catTags, User user) {
        if(!this.user.equals(user)){
            throw new IllegalArgumentException("cat detail을 수정할 권한이 없습니다!");
        }
        this.catImages.clear();
        List<CatImage> catImages = CatImage.ConvertListStringToListCatImage(cat, this, catDetailUpdateRequestDto.getCatImages());
        this.catImages.addAll(catImages);
        this.catTags.clear();
        this.catTags.addAll(catTags);
        this.food = catDetailUpdateRequestDto.isFood();
        this.diary = catDetailUpdateRequestDto.getDiary();
        this.water = catDetailUpdateRequestDto.isWater();
        this.snack = catDetailUpdateRequestDto.isSnack();
        updateModifiedAt();
    }


/*    @OneToMany(mappedBy = "CatDetail", cascade = {CascadeType.REMOVE})
    private List<CatTag> catTagList  = new ArrayList<>();*/

}
