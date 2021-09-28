package streetcatshelter.discatch.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import streetcatshelter.discatch.domain.Cat;

public interface CatRepository extends JpaRepository<Cat, Long> {
    Page<Cat> findAllByLocation(Pageable pageable, String location);
}

