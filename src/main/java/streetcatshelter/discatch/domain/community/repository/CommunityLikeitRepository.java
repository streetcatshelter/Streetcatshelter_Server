package streetcatshelter.discatch.domain.community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import streetcatshelter.discatch.domain.community.domain.Community;
import streetcatshelter.discatch.domain.community.domain.CommunityLikeit;
import streetcatshelter.discatch.domain.user.domain.User;

public interface CommunityLikeitRepository extends JpaRepository<CommunityLikeit, Long> {
    Boolean existsByCommunityAndUser(Community community, User user);
    void deleteByCommunityAndUser(Community community, User user);
}
