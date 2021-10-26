package streetcatshelter.discatch.domain.cat.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import streetcatshelter.discatch.domain.cat.domain.CatDetail;
import streetcatshelter.discatch.domain.user.domain.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public interface CatDetailRepository extends JpaRepository<CatDetail, Long> {

    List<CatDetail>findAllByCatId(Long catId);
    Page<CatDetail> findAllByCatId(Pageable pageable, Long catId);
    ArrayList<CatDetail> findAllByUser(User user);
    CatDetail findFirstByOrderByIdDesc();
    int countAllByUser_UserSeqAndCatId(Long userSeq, Long catId);
    int countAllByUserAndModifiedAtBetween(User user, LocalDateTime start, LocalDateTime end);



}
