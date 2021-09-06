package streetcatshelter.discatch.domain;


import javax.persistence.*;
import java.util.List;

@Entity
public class Cat {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long catId;

    @Column(nullable = false)
    private String catName;

    @Enumerated
    @Column(nullable = false)
    private Neutering neutering;

    @Column
    @OneToMany
    private List<CatTag> catTag;

}
