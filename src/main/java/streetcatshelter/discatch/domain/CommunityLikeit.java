package streetcatshelter.discatch.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import streetcatshelter.discatch.domain.user.domain.User;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CommunityLikeit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "USER_USER_SEQ")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

    @JoinColumn(name = "COMMUNITY_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Community community;

    public CommunityLikeit(Community community, User user) {
        this.user = user;
        this.community = community;
    }
}
