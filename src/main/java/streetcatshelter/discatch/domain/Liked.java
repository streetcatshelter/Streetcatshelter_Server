package streetcatshelter.discatch.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import streetcatshelter.discatch.domain.cat.domain.Cat;
import streetcatshelter.discatch.domain.cat.domain.CatDetail;
import streetcatshelter.discatch.domain.user.domain.User;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Liked {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_USER_SEQ")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CAT_ID")
    private Cat cat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CAT_DETAIL_ID")
    private CatDetail catDetail;

    public Liked(CatDetail catDetail, User user){
        this.catDetail =catDetail;
        this.user = user;
    }

    public Liked(Cat cat, User user){
        this.cat =cat;
        this.user = user;
    }

}
