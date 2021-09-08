package streetcatshelter.discatch.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import streetcatshelter.discatch.dto.CatRequestDto;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class CatImage {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long catImageId;

    @Column(nullable = false)
    private String catImage;

/*    public CatImage(CatRequestDto catRequestDto) {
        this.catId = catRequestDto.getCatId();
        this.catImage = catRequestDto.getCatImage();
    }*/
}
