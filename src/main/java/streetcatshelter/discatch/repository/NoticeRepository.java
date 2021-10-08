package streetcatshelter.discatch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import streetcatshelter.discatch.domain.Notice;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    List<Notice> findAllByOrderByIdDesc();
}
