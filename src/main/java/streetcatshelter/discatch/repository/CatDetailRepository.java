package streetcatshelter.discatch.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import streetcatshelter.discatch.domain.CatDetail;
import streetcatshelter.discatch.domain.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public interface CatDetailRepository extends JpaRepository<CatDetail, Long> {

    List<CatDetail>findAllByCatId(Long catId);
    Page<CatDetail> findAllByCatId(Pageable pageable, Long catId);
    ArrayList<CatDetail> findAllByUser(User user);
    ArrayList<CatDetail> findAllByUserAndModifiedAtBetween(User user, LocalDateTime start, LocalDateTime end);
    CatDetail findFirstByOrderByIdDesc();
    int countAllByUser_UserSeqAndCatId(Long userSeq, Long catId);
    int countAllByUserAndModifiedAtBetween(User user, LocalDateTime start, LocalDateTime end);



}
