package streetcatshelter.discatch.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import streetcatshelter.discatch.domain.Liked;

public interface LikedRepository extends JpaRepository<Liked,Long> {


    Liked findByCatDetailIdAndUser_UserSeq(Long catDetailId, Long userSeq);
    boolean existsByUser_UserSeqAndCatDetailId(Long userSeq,Long catDetailId);
}
