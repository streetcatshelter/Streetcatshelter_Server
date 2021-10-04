package streetcatshelter.discatch.domain;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class Liked {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CAT_DETAIL_ID")
    private CatDetail catDetail;

    public Liked(CatDetail catDetail, User user){
        this.catDetail =catDetail;
        this.user = user;
    }

}
