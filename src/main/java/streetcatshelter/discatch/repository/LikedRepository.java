package streetcatshelter.discatch.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import streetcatshelter.discatch.domain.Liked;

import java.util.List;

public interface LikedRepository extends JpaRepository<Liked,Long> {


    Liked findByCatDetailIdAndUser_UserSeq(Long catDetailId, Long userSeq);
    Liked findByCatIdAndUser_UserSeq(Long catId, Long userSeq);
    List<Liked> findAllByUser_UserSeq(Long userSeq);
    boolean existsByUser_UserSeqAndCatDetailId(Long userSeq,Long catDetailId);
}
