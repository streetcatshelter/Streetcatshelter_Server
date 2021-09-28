package streetcatshelter.discatch.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CatTag extends TimeStamped {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private String tag;

    @JoinColumn(name = "CAT_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Cat cat;

    @JoinColumn(name = "CATDETAIL_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private CatDetail catDetail;

    public CatTag(Cat cat, String tag) {
        this.cat = cat;
        this.tag = tag;
    }
}
