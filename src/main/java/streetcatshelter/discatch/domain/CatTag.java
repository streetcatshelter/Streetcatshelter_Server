package streetcatshelter.discatch.domain;

import javax.persistence.*;

@Entity
public class CatTag {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long catId;

    @Column(nullable = false)
    private String tag;


}
