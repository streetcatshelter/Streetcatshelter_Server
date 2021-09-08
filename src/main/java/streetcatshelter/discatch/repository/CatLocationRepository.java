package streetcatshelter.discatch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import streetcatshelter.discatch.domain.CatLocation;

public interface CatLocationRepository extends JpaRepository<CatLocation, Long> {
}
