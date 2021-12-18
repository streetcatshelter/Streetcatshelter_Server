package streetcatshelter.discatch.domain.cat.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import streetcatshelter.discatch.domain.cat.domain.CatImage;

public interface CatImageRepository extends JpaRepository<CatImage, Long> {

    Page<CatImage> findAllByCatId(Pageable pageable, Long catId);
}
