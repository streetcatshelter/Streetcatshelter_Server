package streetcatshelter.discatch.domain.cat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import streetcatshelter.discatch.domain.cat.domain.CatTag;

public interface CatTagRepository extends  JpaRepository<CatTag, Long> {
}
