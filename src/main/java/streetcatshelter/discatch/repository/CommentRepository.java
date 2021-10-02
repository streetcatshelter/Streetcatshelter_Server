package streetcatshelter.discatch.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import streetcatshelter.discatch.domain.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    int countAllByCommunityId(Long communityId);
    List<Comment> findAllByCatId(Long catId);
    Page<Comment> findAllByCatDetailId(Pageable pageable, Long catDetailId);
}
