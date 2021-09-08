package streetcatshelter.discatch.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import streetcatshelter.discatch.dto.CatRequestDto;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Cat extends TimeStamped{

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
    private List<CatTag> Tags;

    @Column
    @OneToMany
    private List<CatLocation> catLocations;

    @Column
    @OneToMany
    private List<CatImage> catImages;

    public Cat(CatRequestDto catRequestDto) {
        this.catName = catRequestDto.getCatName();
        this.neutering = catRequestDto.getNeutering();
    }
}
