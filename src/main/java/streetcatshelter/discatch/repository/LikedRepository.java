package streetcatshelter.discatch.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import streetcatshelter.discatch.domain.Liked;

import java.util.List;

public interface LikedRepository extends JpaRepository<Liked,Long> {


    Liked findByCatDetailIdAndUser_UserSeq(Long catDetailId, Long userSeq);
    Liked findByCatIdAndUser_UserSeq(Long catId, Long userSeq);
    List<Liked> findAllByUser_UserSeq(Long userSeq);
    Page<Liked> findAllByUser_UserSeq(Long userSeq, Pageable pageable);
    boolean existsByUser_UserSeqAndCatDetailId(Long userSeq,Long catDetailId);
    int countAllByUser_UserSeq(Long userSeq);
    boolean existsByCatIdAndUser_UserSeq(Long catId, Long userSeq);
}
