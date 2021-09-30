package streetcatshelter.discatch.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import streetcatshelter.discatch.domain.Community;

public interface CommunityRepository extends JpaRepository<Community, Long> {
    Page<Community> findAllByCategoryAndLocation(Pageable pageable, String category, String location);
}
