package streetcatshelter.discatch.domain.cat.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import streetcatshelter.discatch.domain.cat.domain.Cat;

public interface CatRepository extends JpaRepository<Cat, Long> {
    Page<Cat> findAllByLocation(Pageable pageable, String location);
    int countAllByUser_UserSeq(Long userSeq);
}

