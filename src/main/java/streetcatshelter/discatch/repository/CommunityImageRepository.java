package streetcatshelter.discatch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import streetcatshelter.discatch.domain.CommunityImage;

public interface CommunityImageRepository extends JpaRepository<CommunityImage, Long> {
     void deleteAllByCommunityId(Long communityId);
}
