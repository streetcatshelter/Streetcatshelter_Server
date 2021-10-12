package streetcatshelter.discatch.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import streetcatshelter.discatch.domain.CatDetail;
import streetcatshelter.discatch.domain.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

public interface CatDetailRepository extends JpaRepository<CatDetail, Long> {

    List<CatDetail>findAllByCatId(Long catId);
    Page<CatDetail> findAllByCatId(Pageable pageable, Long catId);
    ArrayList<CatDetail> findAllByUser(User user);
    CatDetail findFirstByOrderByIdDesc();
    int countAllByUser_UserSeqAndCatId(Long userSeq, Long catId);



}
