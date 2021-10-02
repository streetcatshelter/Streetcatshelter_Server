package streetcatshelter.discatch.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class CatImage extends TimeStamped{

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CAT_ID")
    @JsonIgnore
    private Cat cat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CAT_DETAIL_ID")
    @JsonIgnore
    private CatDetail catDetail;

    @Column
    private String image;

    public CatImage(Cat cat,CatDetail catDetail, String image){
        this.cat = cat;
        this.catDetail = catDetail;
        this.image = image;
    }


}
