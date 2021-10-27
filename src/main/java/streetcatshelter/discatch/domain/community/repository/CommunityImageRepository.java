package streetcatshelter.discatch.domain.community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import streetcatshelter.discatch.domain.community.domain.CommunityImage;

public interface CommunityImageRepository extends JpaRepository<CommunityImage, Long> {
     void deleteAllByCommunityId(Long communityId);
}
