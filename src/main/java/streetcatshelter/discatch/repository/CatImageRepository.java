package streetcatshelter.discatch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import streetcatshelter.discatch.domain.CatImage;

public interface CatImageRepository extends JpaRepository<CatImage, Long> {
}
