package streetcatshelter.discatch.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import streetcatshelter.discatch.domain.Cat;

import java.awt.*;

public interface CatRepository extends JpaRepository<Cat, Long> {

}
