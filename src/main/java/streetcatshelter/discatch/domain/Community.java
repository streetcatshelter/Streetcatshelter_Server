package streetcatshelter.discatch.domain;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;


@Entity
@Getter
@NoArgsConstructor
public class Community {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long CommunityId;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private Point location;
}
