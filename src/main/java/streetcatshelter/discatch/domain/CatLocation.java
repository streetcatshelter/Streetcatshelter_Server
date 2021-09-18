package streetcatshelter.discatch.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import streetcatshelter.discatch.dto.CatRequestDto;

import javax.persistence.*;
import java.awt.*;


@Entity
@Getter
@NoArgsConstructor
public class CatLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long catLocationId;

    @Column(nullable = false)
    private Point catLocation;

    public CatLocation(CatRequestDto catRequestDto) {
        this.catLocation = catRequestDto.getLocation();
    }
}
