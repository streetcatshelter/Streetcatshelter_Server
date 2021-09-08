package streetcatshelter.discatch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import streetcatshelter.discatch.domain.CatTag;

public interface CatTagRepository extends JpaRepository<CatTag, Long> {
}
