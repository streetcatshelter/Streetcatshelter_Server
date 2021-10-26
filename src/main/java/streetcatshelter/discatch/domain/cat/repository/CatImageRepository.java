package streetcatshelter.discatch.domain.cat.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import streetcatshelter.discatch.domain.cat.domain.CatImage;

import java.util.List;

public interface CatImageRepository extends JpaRepository<CatImage, Long> {

    List<CatImage> findAllByCatId(Pageable pageable, Long catId);
}
