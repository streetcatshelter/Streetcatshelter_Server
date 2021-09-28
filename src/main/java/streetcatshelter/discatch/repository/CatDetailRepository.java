package streetcatshelter.discatch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import streetcatshelter.discatch.domain.CatDetail;

import java.util.List;

public interface CatDetailRepository extends JpaRepository<CatDetail, Long> {
    List<CatDetail>findAllByCat(Long catId);
}
