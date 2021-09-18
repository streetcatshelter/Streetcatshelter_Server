package streetcatshelter.discatch.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import streetcatshelter.discatch.dto.CatRequestDto;

import javax.persistence.*;


@Entity
@Getter
@NoArgsConstructor
public class CatTag {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long catTagId;

    @Column(nullable = false)
    private String tag;

/*
    public CatTag(CatRequestDto catRequestDto) {
        this.tag = catRequestDto.getCatTags();
    }
*/
}
