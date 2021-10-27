package streetcatshelter.discatch.domain.community.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "communityImage")
public class CommunityImage{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String image;

    @JoinColumn(name = "COMMUNITY_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Community community;

    public CommunityImage(Community community, String image) {
        this.community = community;
        this.image = image;
    }

}
